package com.zb.data.poi;

import com.zb.data.poi.entity.*;
import com.zb.data.poi.mapper.*;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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

    @Autowired
    private OpRoadsegmentParkinglotRelationDao roadsegmentParkinglotRelationDao;

    @Autowired
    private OpParkinglotDao opParkinglotDao;

    @Autowired
    private OpParkinglotSensorRelationDao sensorRelationDao;

    @Autowired
    private OpParkingDao parkingDao;

    @Autowired
    private OpConstructionStatusDao constructionStatusDao;

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
        InputStream is = new FileInputStream("d:/excel-poi/sourceData/data-rule.xlsx");

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
                ruleEntity.setEffectiveStatus("001");
                ruleEntity.setVersion("--");
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
                    String timeType = rowData.getCell(8).toString();
                    switch (timeType){
                        case "时间段":
                            btp.setType("001");
                            break;
                        case "间隔":
                            btp.setType("002");
                            break;
                    }

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
                    String timeType = rowData.getCell(12).toString();
                    switch (timeType){
                        case "时间段":
                            btp.setType("001");
                            break;
                        case "间隔":
                            btp.setType("002");
                            break;
                    }
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
                    String timeType = rowData.getCell(16).toString();
                    switch (timeType){
                        case "时间段":
                            btp.setType("001");
                            break;
                        case "间隔":
                            btp.setType("002");
                            break;
                    }
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

        InputStream is = new FileInputStream("d:/excel-poi/sourceData/data-rule-parking-new.xlsx");

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
                String parkingName = rowData.getCell(1).toString();
                int parkingId = 0;
                if (parkingName !=null){
                    parkingId = ruleRelationDao.getParkingIdByName(parkingName)==0?0:ruleRelationDao.getParkingIdByName(parkingName);
                    if (parkingId >0){
                        relationEntity.setParkingId(parkingId);
                    }

                }

                String ruleName = rowData.getCell(2).toString();
                int ruleId = 0;
                if (ruleName != null){
                    ruleId = ruleRelationDao.getRuleIdByName(ruleName);
                    relationEntity.setChargingRuleId(ruleId);
                }
                if ( parkingId >0 && ruleId >0){
                    list.add(relationEntity);
                }

            }

        }
        System.out.println(list);
        ruleRelationDao.batchAddRuleRelation(list);

    }

    @Test
    public void testAddRoadParkingLotRelation() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/data-parkinglot.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        List<OpRoadsegmentParkinglotRelationEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount -1; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                OpRoadsegmentParkinglotRelationEntity relationEntity = new OpRoadsegmentParkinglotRelationEntity();
                String str1 = rowData.getCell(0).toString();
                String[] split1 = str1.split("\\.");
                relationEntity.setParkinglotId(Integer.parseInt(split1[0]));

                String str2 = rowData.getCell(4).toString();
                String[] split2 = str2.split("\\.");
                relationEntity.setRoadSegmentId(Integer.parseInt(split2[0]));

                list.add(relationEntity);
            }

        }
        System.out.println(list);
        roadsegmentParkinglotRelationDao.batchAddRoadParkingLotRelation(list);

    }

    //添加车位信息
    @Test
    public void testAddParkingLot() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/data-parkinglot.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        List<OpParkinglotEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount -1; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                OpParkinglotEntity parkingLot = new OpParkinglotEntity();
                String str1 = rowData.getCell(2).toString();
                String[] split1 = str1.split("\\.");
                parkingLot.setParkingId(Integer.parseInt(split1[0]));

                String code = rowData.getCell(4).toString();
                parkingLot.setCode(code);
                parkingLot.setIsInvalidate("001");
                parkingLot.setSiteId(1);

                String str2 = rowData.getCell(5).toString();
                String[] split2 = str2.split("\\.");
                parkingLot.setRoadsegmentId(Integer.parseInt(split2[0]));

                list.add(parkingLot);
            }

        }
        System.out.println(list);
        opParkinglotDao.batchAddParkingLot(list);

    }

    //地磁 code
    @Test
    public void testAddParkingLotSenorRelation() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/sourceData/data-parking-senor.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        List<OpParkinglotSensorRelationEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < 633; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                OpParkinglotSensorRelationEntity relationEntity = new OpParkinglotSensorRelationEntity();
                String parkingName = rowData.getCell(0).toString();

                String roadName = rowData.getCell(1).toString();
                String code = rowData.getCell(2).toString();

                //地磁编号
                String senorCode = rowData.getCell(3).toString();
                relationEntity.setSensorCode(senorCode);

                int parkingLotId = opParkinglotDao.getParkingLotIdByCondition(code,roadName,parkingName);
                relationEntity.setParkinglotId(parkingLotId);

                list.add(relationEntity);
            }

        }
        System.out.println(list);
        sensorRelationDao.batchAddSenorRelation(list);

    }


    //修改停车场经纬度
    @Test
    public void testUpdateParking() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/sourceData/data-parking.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        //List<OpParkinglotEntity> list = new ArrayList<>();
        List<OpParkingEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < 86; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                OpParkingEntity parking = new OpParkingEntity();
                String coordinate = rowData.getCell(5).toString();
                if (coordinate != null && coordinate != ""){
                    parking.setCoordinate(coordinate);
                }else {
                    break;
                }

                String name = rowData.getCell(1).toString();
                if (name != null && name != ""){
                    parking.setName(name);
                }else {
                    break;
                }
                list.add(parking);

                /*if (coordinate != null && name != null){
                    parking.setCoordinate(coordinate);
                    parking.setName(name);
                    int i = parkingDao.updateParking(coordinate, name);
                    System.out.println("执行结果"+i);

                }*/

            }

        }
        //System.out.println(list);
        //opParkinglotDao.batchAddParkingLot(list);
        if(list != null && list.size() >0){
            parkingDao.batchUpdateParking(list);
        }


    }

    //更新construction
    @Test
    public void testUpdateConstruction() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/sourceData/data-time.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        //List<OpParkinglotEntity> list = new ArrayList<>();
        List<OpConstructionStatusEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < 85; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空


                for (int i = 1;i<=6;i++){
                    // 读取cell
                    OpConstructionStatusEntity construction = new OpConstructionStatusEntity();
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String name = rowData.getCell(0).toString();

                    int parkingId = parkingDao.getParkingIdByName(name);

                    Cell c1 = rowData.getCell(1);

                    if (HSSFDateUtil.isCellDateFormatted(c1)) {//日期
                        System.out.print("【日期】");
                        Date date = c1.getDateCellValue();
                        construction.setPlanFinishDate(new DateTime(date).toString("yyyy-MM-dd"));
                        construction.setActualFinishDate(new DateTime(date).toString("yyyy-MM-dd"));
                    }
                    if (parkingId != 0){
                        construction.setParkingId(parkingId);
                    }

                    construction.setSiteId(1);
                    switch (i){
                        case 1:
                            construction.setStatus("001");
                            break;
                        case 2:
                            construction.setStatus("002");
                            break;
                        case 3:
                            construction.setStatus("003");
                            break;
                        case 4:
                            construction.setStatus("004");
                            break;
                        case 5:
                            construction.setStatus("005");
                            break;
                        case 6:
                            construction.setStatus("006");
                            break;

                    }

                    list.add(construction);


                }


                /*Cell c2 = rowData.getCell(2);
                if (HSSFDateUtil.isCellDateFormatted(c2)) {//日期
                    System.out.print("【日期】");
                    Date date = c2.getDateCellValue();
                    construction.setActualFinishDate(new DateTime(date).toString("yyyy-MM-dd"));
                }*/



            }

        }
        //System.out.println(list);
        //opParkinglotDao.batchAddParkingLot(list);
        if(list != null && list.size() >0){
            constructionStatusDao.batchAddConstruction(list);
        }


    }




}
