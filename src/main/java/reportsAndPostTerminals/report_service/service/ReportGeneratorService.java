package reportsAndPostTerminals.report_service.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import reportsAndPostTerminals.posterminal.model.entity.Transaction;
import reportsAndPostTerminals.posterminal.model.enums.Curr;
import reportsAndPostTerminals.posterminal.repository.TransactionRepository;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class ReportGeneratorService {
    private final TransactionRepository transactionRepository;
    private final ReportsService reportsService;
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Transaction> transactions = transactionRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Date/Time");
        header.createCell(2).setCellValue("Currency");
        header.createCell(3).setCellValue("Amount");
        header.createCell(4).setCellValue("From Score");
        header.createCell(5).setCellValue("To Device");

        int rowNum = 1;
        for (Transaction tx : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(tx.getId());
            row.createCell(1).setCellValue(tx.getOperationDateTime().toString());
            row.createCell(2).setCellValue(tx.getCurr() != null ? tx.getCurr().toString() : "—");
            row.createCell(3).setCellValue(tx.getAmount());
            row.createCell(4).setCellValue(tx.getFromScore());
            row.createCell(5).setCellValue(tx.getToDevice());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
        response.flushBuffer();
    }

    public void exportToExcelAllPeriodsGrouped(HttpServletResponse response) throws IOException {
        List<Object[]> dayData = reportsService.calculateAmountForLastDay();
        List<Object[]> weekData = reportsService.calculateAmountForLastWeek();
        List<Object[]> monthData = reportsService.calculateAmountForLastMonth();

        // Курсы валют к KGS (примерные, можно заменить на актуальные или получать из сервиса)
        Map<Curr, Double> exchangeRates = Map.of(
                Curr.KGS, 1.0,
                Curr.USD, 89.5,
                Curr.EUR, 96.2
        );

        // Map: Currency -> (Period -> TotalSum)
        Map<Curr, Map<String, Double>> dataMap = new TreeMap<>(Comparator.comparing(Enum::name));

        BiConsumer<List<Object[]>, String> insertData = (list, period) -> {
            for (Object[] arr : list) {
                Curr currency = (Curr) arr[0];
                Double total = (Double) arr[1];
                dataMap.computeIfAbsent(currency, k -> new HashMap<>()).put(period, total);
            }
        };

        insertData.accept(dayData, "Last Day");
        insertData.accept(weekData, "Last Week");
        insertData.accept(monthData, "Last Month");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");

        // Заголовок
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Currency");
        header.createCell(1).setCellValue("Period");
        header.createCell(2).setCellValue("TotalSum");
        header.createCell(3).setCellValue("Sum in KGS");

        int rowNum = 1;
        double totalInKgs = 0.0;

        for (Map.Entry<Curr, Map<String, Double>> entry : dataMap.entrySet()) {
            Curr currency = entry.getKey();
            Map<String, Double> periodMap = entry.getValue();
            double rate = exchangeRates.getOrDefault(currency, 1.0);

            for (String period : List.of("Last Day", "Last Week", "Last Month")) {
                if (periodMap.containsKey(period)) {
                    double value = periodMap.get(period);
                    double valueInKgs = value * rate;
                    totalInKgs += valueInKgs;

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(currency.name());
                    row.createCell(1).setCellValue(period);
                    row.createCell(2).setCellValue(value);
                    row.createCell(3).setCellValue(valueInKgs);
                }
            }
        }

        // Пустая строка + итоговая сумма
        rowNum++;
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(2).setCellValue("Total in KGS:");
        totalRow.createCell(3).setCellValue(totalInKgs);

        // Автоширина колонок
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ответ клиенту
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
        response.flushBuffer();
    }


}
