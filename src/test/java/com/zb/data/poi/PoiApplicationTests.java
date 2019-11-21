package com.zb.data.poi;

import com.zb.data.poi.entity.*;
import com.zb.data.poi.mapper.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiApplicationTests {

    @Autowired
    private DemoDataMapper demoDataMapper;

    @Autowired
    private OpChargingRuleDao ruleDao;

    @Autowired
    private OpChargingRuleTimeperiodDao timeperiodDao;

    @Autowired
    private OpChargingRuleBtpStdDao btpStdDao;

    @Autowired
    private OpParkingChargingRuleRelationDao ruleRelationDao;

    @Test
    public void testQueryAll(){
        List<DemoData> demoData = demoDataMapper.queryAll();
        System.out.println(demoData);
    }

    @Test
    public void testAdd() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/demo.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容
        Row rowTitle = sheet.getRow(0);
        if (rowTitle != null) {// 行不为空
            // 读取cell
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitle.getCell(cellNum);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + "|");
                }
            }
            System.out.println();
        }

        List<DemoData> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                DemoData demoData = new DemoData();
                // 读取cell
                //int cellCount = rowTitle.getPhysicalNumberOfCells();
                demoData.setString(rowData.getCell(0).toString());
                Cell cell1 = rowData.getCell(1);
                Date dateCellValue = cell1.getDateCellValue();
                demoData.setDate(dateCellValue);
                demoData.setDoubleData(Double.valueOf(rowData.getCell(2).toString()));

                list.add(demoData);
            }

        }
        System.out.println(list);
        demoDataMapper.batchAddDemoData(list);

    }

    @Test
    public void testAddChargeRule() throws Exception{
        InputStream is = new FileInputStream("d:/excel-poi/data-rule-btp.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容
        List<OpChargingRuleEntity> chargList = new ArrayList<>();
        List<OpChargingRuleTimeperiodEntity> timeList = new ArrayList<>();
        List<OpChargingRuleBtpStdEntity> btpList = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 2; rowNum < rowCount -1; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                //DemoData demoData = new DemoData();
                OpChargingRuleEntity ruleEntity = new OpChargingRuleEntity();
                OpChargingRuleTimeperiodEntity timeEntity = new OpChargingRuleTimeperiodEntity();

                String s = String.valueOf(rowData.getCell(0).getNumericCellValue());
                String[] split = s.split("\\.");
                int ruleId = Integer.parseInt(split[0]);
                ruleEntity.setId(ruleId);
                ruleEntity.setName(rowData.getCell(1).toString());
                ruleEntity.setCode(rowData.getCell(2).toString());
                ruleEntity.setCarPlateColor(rowData.getCell(3).toString());
                ruleEntity.setChargingUpperLimit(new BigDecimal(rowData.getCell(4).toString()));
                ruleEntity.setComments(null);
                ruleEntity.setEffectiveStatus("001");
                /**
                 * #运营端 延时生效 bug
                 * #effective_type 001立即生效 002延时生效
                 * #effective_status 001 待生效,002,已生效,003 已失效
                 * #release_status 001，草稿 ，002 发布 ,003 撤销
                 * #version 版本
                 * #status 1. 新增 2 .更新 3.删除 延迟更新测试3-勿动 TEST1004
                 * 历史记录1 测试草稿
                 */
                ruleEntity.setSiteId(1);
                ruleEntity.setEffectiveType("001");
                ruleEntity.setEffectiveTime(null);
                ruleEntity.setEffectiveStatus("002");
                ruleEntity.setVersion("1.0");
                ruleEntity.setReleaseStatus("001");
                ruleEntity.setReleaseErrorReason(null);
                ruleEntity.setStatus("001");
                ruleEntity.setCreateDate(new Date());
                ruleEntity.setCreateUser("-1");
                ruleEntity.setUpdateDate(new Date());
                ruleEntity.setUpdateUser("-1");
                //time
                timeEntity.setId(ruleId);
                timeEntity.setSiteId(1);
                timeEntity.setChargingRuleId(ruleId);
                Date startDate = rowData.getCell(6).getDateCellValue();
                String form1 = new DateTime(startDate).toString("HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date start = sdf.parse(form1);

                timeEntity.setStartTime(start);


                Date endDate = rowData.getCell(7).getDateCellValue();
                String form2 = new DateTime(endDate).toString("HH:mm:ss");
                Date end = sdf.parse(form2);
                timeEntity.setEndTime(end);

                //遍历btp

                Cell cell = rowData.getCell(8);

                if (cell != null){
                    OpChargingRuleBtpStdEntity btp = new OpChargingRuleBtpStdEntity();

                    btp.setChargingRuleBtpId(ruleId);
                    btp.setSiteId(1);
                    btp.setType("001");
                    btp.setPeriodNo(1);
                    //数量
                    String s1 = rowData.getCell(9).toString();
                    String[] split1 = s1.split("\\.");

                    btp.setCount(Integer.parseInt(split1[0]));
                    btp.setPeriodName("第一阶段收费标准");
                    //1. 小时 2. 半小时  3. 一刻钟
                    String str = rowData.getCell(10).toString();
                    switch (str){
                        case "/半小时":
                            btp.setTimeIntervalType("002");
                            break;
                        case "/小时":
                            btp.setTimeIntervalType("001");
                            break;
                        case "/一刻钟":
                            btp.setTimeIntervalType("003");
                            break;
                    }

                    //收费金额
                    btp.setTimeChargingAmount(new BigDecimal(rowData.getCell(11).toString()));

                    btpList.add(btp);
                }

                Cell c2 = rowData.getCell(12);
                if (c2 != null){

                    OpChargingRuleBtpStdEntity btp = new OpChargingRuleBtpStdEntity();
                    btp.setSiteId(1);
                    btp.setChargingRuleBtpId(ruleId);
                    btp.setType("001");
                    btp.setPeriodNo(2);
                    //数量
                    String s1 = rowData.getCell(13).toString();
                    String[] split1 = s1.split("\\.");

                    btp.setCount(Integer.parseInt(split1[0]));
                    btp.setPeriodName("第二阶段收费标准");
                    //1. 小时 2. 半小时  3. 一刻钟
                    //btp.setTimeIntervalType("001");
                    String str = rowData.getCell(14).toString();
                    switch (str){
                        case "/半小时":
                            btp.setTimeIntervalType("002");
                            break;
                        case "/小时":
                            btp.setTimeIntervalType("001");
                            break;
                        case "/一刻钟":
                            btp.setTimeIntervalType("003");
                            break;
                    }
                    //收费金额
                    btp.setTimeChargingAmount(new BigDecimal(rowData.getCell(15).toString()));

                    btpList.add(btp);

                }

                Cell c3 = rowData.getCell(16);
                if (c3 != null){
                    OpChargingRuleBtpStdEntity btp = new OpChargingRuleBtpStdEntity();
                    btp.setSiteId(1);
                    btp.setChargingRuleBtpId(ruleId);
                    btp.setType("001");
                    btp.setPeriodNo(3);
                    //数量
                    String s1 = rowData.getCell(17).toString();
                    String[] split1 = s1.split("\\.");
                    btp.setCount(Integer.parseInt(split1[0]));

                    btp.setPeriodName("第三阶段收费标准");
                    //1. 小时 2. 半小时  3. 一刻钟
                    //btp.setTimeIntervalType("001");
                    String str = rowData.getCell(18).toString();
                    switch (str){
                        case "/半小时":
                            btp.setTimeIntervalType("002");
                            break;
                        case "/小时":
                            btp.setTimeIntervalType("001");
                            break;
                        case "/一刻钟":
                            btp.setTimeIntervalType("003");
                            break;
                    }

                    //收费金额
                    btp.setTimeChargingAmount(new BigDecimal(rowData.getCell(19).toString()));

                    btpList.add(btp);
                }

                //list.add(demoData);
                chargList.add(ruleEntity);
                timeList.add(timeEntity);

            }

        }

        ruleDao.batchAddCharging(chargList);
        timeperiodDao.batchAddChargingTime(timeList);
        btpStdDao.batchAddBtp(btpList);
    }


    @Test
    public void testAddRuleParking() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/data-rule-parking.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        List<OpParkingChargingRuleRelationEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount -1; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                OpParkingChargingRuleRelationEntity relationEntity = new OpParkingChargingRuleRelationEntity();
                String str1 = rowData.getCell(2).toString();
                String[] split1 = str1.split("\\.");
                relationEntity.setParkingId(Integer.parseInt(split1[0]));

                String str2 = rowData.getCell(4).toString();
                String[] split2 = str2.split("\\.");
                relationEntity.setChargingRuleId(Integer.parseInt(split2[0]));

                list.add(relationEntity);
            }

        }
        System.out.println(list);
        ruleRelationDao.batchAddRuleRelation(list);

    }

}
