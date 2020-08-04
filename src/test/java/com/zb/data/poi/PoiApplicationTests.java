package com.zb.data.poi;

import com.zb.data.poi.entity.*;
import com.zb.data.poi.mapper.*;
import com.zb.data.poi.model.ParkingModel;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private OpSeedUserDao seedUserDao;

    @Autowired
    private OpUserParkingDao opUserParkingDao;

    @Autowired
    private PictureHistoryMapper pictureHistoryMapper;

    @Autowired
    private FlowMapper flowMapper;

    @Autowired
    private CwParkingOrderMapper cwParkingOrderMapper;

    @Test
    public void testQueryAll() {
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
    public void testAddChargeRule() throws Exception {
        InputStream is = new FileInputStream("d:/excel-poi/sourceData/data-rule.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容
        List<OpChargingRuleEntity> chargList = new ArrayList<>();
        List<OpChargingRuleTimeperiodEntity> timeList = new ArrayList<>();
        List<OpChargingRuleBtpStdEntity> btpList = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 2; rowNum < rowCount - 1; rowNum++) {
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

                if (cell != null) {
                    OpChargingRuleBtpStdEntity btp = new OpChargingRuleBtpStdEntity();

                    btp.setChargingRuleBtpId(ruleId);
                    btp.setSiteId(1);
                    String timeType = rowData.getCell(8).toString();
                    switch (timeType) {
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
                    switch (str) {
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
                if (c2 != null) {

                    OpChargingRuleBtpStdEntity btp = new OpChargingRuleBtpStdEntity();
                    btp.setSiteId(1);
                    btp.setChargingRuleBtpId(ruleId);
                    String timeType = rowData.getCell(12).toString();
                    switch (timeType) {
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
                    switch (str) {
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
                if (c3 != null) {
                    OpChargingRuleBtpStdEntity btp = new OpChargingRuleBtpStdEntity();
                    btp.setSiteId(1);
                    btp.setChargingRuleBtpId(ruleId);
                    String timeType = rowData.getCell(16).toString();
                    switch (timeType) {
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
                    switch (str) {
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
        for (int rowNum = 1; rowNum < rowCount - 1; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                OpParkingChargingRuleRelationEntity relationEntity = new OpParkingChargingRuleRelationEntity();
                String parkingName = rowData.getCell(1).toString();
                int parkingId = 0;
                if (parkingName != null) {
                    parkingId = ruleRelationDao.getParkingIdByName(parkingName) == 0 ? 0 : ruleRelationDao.getParkingIdByName(parkingName);
                    if (parkingId > 0) {
                        relationEntity.setParkingId(parkingId);
                    }

                }

                String ruleName = rowData.getCell(2).toString();
                int ruleId = 0;
                if (ruleName != null) {
                    ruleId = ruleRelationDao.getRuleIdByName(ruleName);
                    relationEntity.setChargingRuleId(ruleId);
                }
                if (parkingId > 0 && ruleId > 0) {
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
        for (int rowNum = 1; rowNum < rowCount - 1; rowNum++) {
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
        for (int rowNum = 1; rowNum < rowCount - 1; rowNum++) {
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

                int parkingLotId = opParkinglotDao.getParkingLotIdByCondition(code, roadName, parkingName);
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
                if (coordinate != null && coordinate != "") {
                    parking.setCoordinate(coordinate);
                } else {
                    break;
                }

                String name = rowData.getCell(1).toString();
                if (name != null && name != "") {
                    parking.setName(name);
                } else {
                    break;
                }
                list.add(parking);

            }

        }
        //System.out.println(list);
        //opParkinglotDao.batchAddParkingLot(list);
        if (list != null && list.size() > 0) {
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

        List<OpUserParkingEntity> userParkingList = new ArrayList<>();
        List<OpSeedUserEntity> seedUserList = new ArrayList<>();
        //List<DemoData> list = new ArrayList<>();
        //List<OpParkinglotEntity> list = new ArrayList<>();
        List<OpConstructionStatusEntity> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < 85; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空

                OpSeedUserEntity seedUser = new OpSeedUserEntity();

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
                if (parkingId != 0) {
                    construction.setParkingId(parkingId);
                }

                construction.setSiteId(1);

                String ownerName = rowData.getCell(1).toString();
                seedUser.setCarownerName(ownerName);
                String mobile = rowData.getCell(2).toString();
                seedUser.setContactMobile(mobile);
                String seedUserType = rowData.getCell(5).toString();
                switch (seedUserType){
                    case "VIP用户":
                        seedUser.setSeedUserType("003");
                        break;
                    case "种子用户":
                        seedUser.setSeedUserType("002");
                        break;
                }
                String plateNo = rowData.getCell(7).toString();
                seedUser.setCarPlateNo(plateNo);
                String parkingName = rowData.getCell(8).toString();
                List<ParkingModel> parkingList = new ArrayList<>();
                if ("全部".equals(parkingName)){
                    parkingList  = parkingDao.queryParkingModeByName(null);
                }else {
                    parkingList =  parkingDao.queryParkingModeByName(parkingName);
                }
                if (!CollectionUtils.isEmpty(parkingList)){
                    for (ParkingModel parkingModel : parkingList) {
                        OpUserParkingEntity userParkingEntity = new OpUserParkingEntity();
                        userParkingEntity.setCarPlateNo(plateNo);
                        userParkingEntity.setParkingNo(parkingModel.getCode());
                        userParkingEntity.setParkingId(parkingModel.getId());
                        userParkingEntity.setSendStatus("001");
                        userParkingEntity.setStatus("1");
                        userParkingEntity.setCreateDatetime(new Date());
                        userParkingEntity.setUpdateDatetime(new Date());

                    }
                }

                seedUser.setSiteId(1);
                //车牌颜色
                String color = rowData.getCell(6).toString();
                /**
                 * 001       蓝牌
                 * 002       黄牌
                 * 003       绿牌
                 * 004       白牌
                 * 005       其他
                 */
                switch (color) {
                    case "蓝牌":
                        seedUser.setCarPlateColor("001");
                        break;
                    case "黄牌":
                        seedUser.setCarPlateColor("002");
                        break;
                    case "绿牌":
                        seedUser.setCarPlateColor("003");
                        break;
                    case "白牌":
                        seedUser.setCarPlateColor("004");
                        break;
                    case "其他":
                        seedUser.setCarPlateColor("005");
                        break;
                }

                list.add(construction);


            }

        }
        //System.out.println(list);
        //opParkinglotDao.batchAddParkingLot(list);
        if (list != null && list.size() > 0) {
            constructionStatusDao.batchAddConstruction(list);
        }


    }


    //批量添加种子用户 单个停车场
    @Test
    public void testBatchAddSeedUser() throws Exception {

        //
        //InputStream is = new FileInputStream("d:/excel-poi/sourceData/实验幼儿园替换车牌录入名单2020.4.13.xlsx");
        InputStream is = new FileInputStream("E:\\吴江work\\种子用户管理\\种子用户录入\\北外苏州附校-录入信息表.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        //List<OpParkinglotEntity> list = new ArrayList<>();
        List<OpParkingEntity> list = new ArrayList<>();
        List<OpUserParkingEntity> userParkingList = new ArrayList<>();
        List<OpSeedUserEntity> seedUserList = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空

                OpSeedUserEntity seedUser = new OpSeedUserEntity();

                String comments = rowData.getCell(9).toString();
                if (StringUtils.isEmpty(comments)){
                    seedUser.setIsDeleted("001");
                }else {
                    seedUser.setIsDeleted("002");
                }

                //停车场name
                String name = rowData.getCell(8).toString();
                //String name = rowData.getCell(8).toString();
                int parkingId = parkingDao.getParkingIdByName(name);

                //String startDate = rowData.getCell(3).toString();
                String startDate = "";
                Cell c3 = rowData.getCell(3);
                if (HSSFDateUtil.isCellDateFormatted(c3)) {//日期
                    System.out.print("【日期】");
                    Date date = c3.getDateCellValue();
                    startDate = new DateTime(date).toString("yyyy-MM-dd");
                }
                //String endDate = rowData.getCell(4).toString();
                String endDate = "";
                Cell c4 = rowData.getCell(4);
                if (HSSFDateUtil.isCellDateFormatted(c4)) {//日期
                    System.out.print("【日期】");
                    Date date = c4.getDateCellValue();
                    endDate = new DateTime(date).toString("yyyy-MM-dd");
                }

                String num = rowData.getCell(0).toString();

                String[] split = num.split("\\.");
                String seedUserId = split[0];
                seedUser.setSiteId(1);
                seedUser.setId(Integer.parseInt(seedUserId));
                String ownerName = rowData.getCell(1).toString();
                seedUser.setCarownerName(ownerName);
                String mobile = rowData.getCell(2).toString();
                String[] split1 = mobile.split("\\.");
                seedUser.setContactMobile(split1[0]);
                String seedUserType = rowData.getCell(5).toString();
                switch (seedUserType){
                    case "VIP用户":
                        seedUser.setSeedUserType("003");
                        break;
                    case "种子用户":
                        seedUser.setSeedUserType("002");
                        break;
                    case "教育类用户":
                        seedUser.setSeedUserType("005");
                        break;

                }
                String plateNo = rowData.getCell(7).toString();
                seedUser.setCarPlateNo(plateNo);
                String parkingName = rowData.getCell(8).toString();
                //String comments = rowData.getCell(9).toString();
                seedUser.setComments(null);

                seedUser.setInvalidateStatus("001");
                List<ParkingModel> parkingList = new ArrayList<>();
                if ("全部".equals(parkingName)){
                    parkingList  = parkingDao.queryParkingModeByName(null);
                }else {
                    parkingList =  parkingDao.queryParkingModeByName(parkingName);
                }
                if (!CollectionUtils.isEmpty(parkingList)){
                    for (ParkingModel parkingModel : parkingList) {
                        OpUserParkingEntity userParkingEntity = new OpUserParkingEntity();
                        userParkingEntity.setCarPlateNo(plateNo);
                        userParkingEntity.setIsWhite(1);
                        userParkingEntity.setParkingNo(parkingModel.getCode());
                        userParkingEntity.setParkingId(parkingModel.getId());
                        userParkingEntity.setSendStatus("001");
                        userParkingEntity.setStatus("1");
                        userParkingEntity.setCreateDatetime(new Date());
                        userParkingEntity.setUpdateDatetime(new Date());
                        userParkingEntity.setStartDate(startDate);
                        userParkingEntity.setEndDate(endDate);
                        userParkingEntity.setSeedUserId(Integer.parseInt(seedUserId));
                        userParkingEntity.setParkingId(parkingId);
                        userParkingList.add(userParkingEntity);
                    }
                }
                //车牌颜色
                String color = rowData.getCell(6).toString();
                /**
                 * 001       蓝牌
                 * 002       黄牌
                 * 003       绿牌
                 * 004       白牌
                 * 005       其他
                 */
                switch (color) {
                    case "蓝牌":
                        seedUser.setCarPlateColor("001");
                        break;
                    case "黄牌":
                        seedUser.setCarPlateColor("002");
                        break;
                    case "绿牌":
                        seedUser.setCarPlateColor("003");
                        break;
                    case "白牌":
                        seedUser.setCarPlateColor("004");
                        break;
                    case "其他":
                        seedUser.setCarPlateColor("005");
                        break;
                }
                seedUserList.add(seedUser);

            }

        }

        if (seedUserList != null && seedUserList.size() >0){
            seedUserDao.batchAddSeedUser(seedUserList);
        }

        if (userParkingList != null && userParkingList.size() >0){
            opUserParkingDao.batchAddUserParking(userParkingList);
        }


    }

    //补充数据 添加一级分类数据
    @Test
    public void testBatchAddUserParking() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/sourceData/202008/种子用户模板_20200804.xlsx");
       // InputStream is = new FileInputStream("E:\\吴江work\\种子用户管理\\种子用户录入\\北外苏州附校-录入信息表.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        //List<DemoData> list = new ArrayList<>();
        //List<OpParkinglotEntity> list = new ArrayList<>();
        List<OpParkingEntity> list = new ArrayList<>();
        List<OpUserParkingEntity> userParkingList = new ArrayList<>();
        List<OpSeedUserEntity> seedUserList = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空

                OpSeedUserEntity seedUser = new OpSeedUserEntity();

                String startDate = "";
                Cell c3 = rowData.getCell(3);
                if (HSSFDateUtil.isCellDateFormatted(c3)) {//日期
                    System.out.print("【日期】");
                    Date date = c3.getDateCellValue();
                    startDate = new DateTime(date).toString("yyyy-MM-dd");
                }
                //String endDate = rowData.getCell(4).toString();
                String endDate = "";
                Cell c4 = rowData.getCell(4);
                if (HSSFDateUtil.isCellDateFormatted(c4)) {//日期
                    System.out.print("【日期】");
                    Date date = c4.getDateCellValue();
                    endDate = new DateTime(date).toString("yyyy-MM-dd");
                }

                String plateNo = rowData.getCell(7).toString();
                //根据车牌号码查询seedUserId;
                int sId = seedUserDao.querySeedUserIdByCarPlateNo(plateNo);

                seedUser.setCarPlateNo(plateNo);
                String parkingName = rowData.getCell(8).toString();

                seedUser.setInvalidateStatus("001");
                List<ParkingModel> parkingList = new ArrayList<>();
                if ("全部".equals(parkingName)){
                    parkingList  = parkingDao.queryParkingModeByName(null);
                }else if ("吴江".equals(parkingName)){
                    parkingList = parkingDao.queryParkingByCityName(parkingName);
                }else {
                    parkingList =  parkingDao.queryParkingModeByName(parkingName);
                }

                if (!CollectionUtils.isEmpty(parkingList)){
                    for (ParkingModel parkingModel : parkingList) {
                        OpUserParkingEntity userParkingEntity = new OpUserParkingEntity();
                        userParkingEntity.setCarPlateNo(plateNo);
                        userParkingEntity.setIsWhite(1);
                        userParkingEntity.setParkingNo(parkingModel.getCode());
                        userParkingEntity.setParkingId(parkingModel.getId());
                        userParkingEntity.setSendStatus("001");
                        userParkingEntity.setStatus("1");
                        userParkingEntity.setCreateDatetime(new Date());
                        userParkingEntity.setUpdateDatetime(new Date());
                        userParkingEntity.setStartDate(startDate);
                        userParkingEntity.setEndDate(endDate);
                        userParkingEntity.setSeedUserId(sId);
                        userParkingList.add(userParkingEntity);
                    }
                }

            }

        }


        if (userParkingList != null && userParkingList.size() >0){
            opUserParkingDao.batchAddUserParking(userParkingList);
        }


    }



    //批量添加种子用户 多个停车场
    @Test
    public void testBatchAddSeedUserSomeParking() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/sourceData/202007/体育局录入名单.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        List<OpUserParkingEntity> userParkingList = new ArrayList<>();
        List<OpSeedUserEntity> seedUserList = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空

                OpSeedUserEntity seedUser = new OpSeedUserEntity();

                String comments = rowData.getCell(9).toString();
                if (StringUtils.isEmpty(comments)){
                    seedUser.setIsDeleted("001");
                }else {
                    seedUser.setIsDeleted("002");
                }

                String startDate = "";
                Cell c3 = rowData.getCell(3);
                if (HSSFDateUtil.isCellDateFormatted(c3)) {//日期
                    System.out.print("【日期】");
                    Date date = c3.getDateCellValue();
                    long time = date.getTime();
                    String start = String.valueOf(time);
                    System.out.println("开始日期"+start);

                    startDate = new DateTime(date).toString("yyyy-MM-dd");
                }
                String endDate = "";
                Cell c4 = rowData.getCell(4);
                if (HSSFDateUtil.isCellDateFormatted(c4)) {//日期
                    System.out.print("【日期】");
                    Date date = c4.getDateCellValue();
                    String end = String.valueOf(date.getTime());
                    System.out.println("结束时间"+end);
                    endDate = new DateTime(date).toString("yyyy-MM-dd");
                }

                String num = rowData.getCell(0).toString();

                String[] split = num.split("\\.");
                String seedUserId = split[0];
                seedUser.setSiteId(1);
                seedUser.setId(Integer.parseInt(seedUserId));
                String ownerName = rowData.getCell(1).toString();
                seedUser.setCarownerName(ownerName);
                String mobile = rowData.getCell(2).toString();
                String[] split1 = mobile.split("\\.");
                seedUser.setContactMobile(split1[0]);
                String seedUserType = rowData.getCell(5).toString();
                switch (seedUserType){
                    case "VIP用户":
                        seedUser.setSeedUserType("003");
                        break;
                    case "种子用户":
                        seedUser.setSeedUserType("002");
                        break;
                    case "教育类用户":
                        seedUser.setSeedUserType("005");
                        break;
                }
                String plateNo = rowData.getCell(7).toString();
                seedUser.setCarPlateNo(plateNo);
                String parkingName = rowData.getCell(8).toString();
                seedUser.setComments(null);

                seedUser.setInvalidateStatus("001");
                List<ParkingModel> parkingList = new ArrayList<>();
                if ("全部".equals(parkingName)){
                    parkingList  = parkingDao.queryParkingModeByName(null);
                }else {
                    //以、分割
                    String[] split2 = parkingName.split("、");
                    if (split2.length >0){
                        for (String pName : split2) {
                            ParkingModel parkingModel = parkingDao.queryParkingByName(pName);
                            parkingList.add(parkingModel);
                        }

                    }else {
                        parkingList =  parkingDao.queryParkingModeByName(parkingName);
                    }
                }
                if (!CollectionUtils.isEmpty(parkingList)){
                    for (ParkingModel parkingModel : parkingList) {
                        OpUserParkingEntity userParkingEntity = new OpUserParkingEntity();
                        userParkingEntity.setCarPlateNo(plateNo);
                        userParkingEntity.setIsWhite(1);
                        userParkingEntity.setParkingNo(parkingModel.getCode());
                        userParkingEntity.setParkingId(parkingModel.getId());
                        userParkingEntity.setSendStatus("001");
                        userParkingEntity.setStatus("1");
                        userParkingEntity.setCreateDatetime(new Date());
                        userParkingEntity.setUpdateDatetime(new Date());
                        userParkingEntity.setStartDate(startDate);
                        userParkingEntity.setEndDate(endDate);
                        userParkingEntity.setSeedUserId(Integer.parseInt(seedUserId));
                        userParkingEntity.setParkingId(parkingModel.getId());
                        userParkingList.add(userParkingEntity);
                    }
                }
                //车牌颜色
                String color = rowData.getCell(6).toString();
                /**
                 * 001       蓝牌
                 * 002       黄牌
                 * 003       绿牌
                 * 004       白牌
                 * 005       其他
                 */
                switch (color) {
                    case "蓝牌":
                        seedUser.setCarPlateColor("001");
                        break;
                    case "黄牌":
                        seedUser.setCarPlateColor("002");
                        break;
                    case "绿牌":
                        seedUser.setCarPlateColor("003");
                        break;
                    case "白牌":
                        seedUser.setCarPlateColor("004");
                        break;
                    case "其他":
                        seedUser.setCarPlateColor("005");
                        break;
                }
                seedUserList.add(seedUser);

            }

        }

        if (seedUserList != null && seedUserList.size() >0){
          //  seedUserDao.batchAddSeedUser(seedUserList);
        }

        if (userParkingList != null && userParkingList.size() >0){
          //  opUserParkingDao.batchAddUserParking(userParkingList);
        }

    }

    //主键自增问题
    @Test
    public void batchAddSeedUserId(){
        List<OpSeedUserEntity> seedUserList = new ArrayList<>();
        for (int i=1948;i<3263;i++){
            OpSeedUserEntity entity = new OpSeedUserEntity();
            entity.setId(i);
            entity.setSiteId(1);
            entity.setCarownerName("hello"+i);
            entity.setIsDeleted("001");
            entity.setInvalidateStatus("001");
            entity.setCarPlateColor("苏A"+i);
            entity.setCarPlateColor("001");
            entity.setContactMobile("13245698456");
            entity.setSeedUserType("001");
            entity.setComments(null);
            seedUserList.add(entity);

            //(`id`,`site_id`,`carowner_name`,`car_plate_color`,
            // `car_plate_no`,`contact_mobile`,`seed_user_type`,
            // `invalidate_status`,comments,`is_deleted`)
        }
        seedUserDao.batchAddSeedUser(seedUserList);

    }

    //操作流水表
    @Test
    public void testOperateFlow() throws Exception {

        InputStream is = new FileInputStream("d:/excel-poi/flow/流水表数据.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容

        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < 2120; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空

                //读取excel中的流水 和出场图片
                String serialNo = rowData.getCell(5).toString();

                String outImgUrl = rowData.getCell(11).toString();

                PictureHistoryEntity historyBySerialNo = pictureHistoryMapper.getHistoryBySerialNo(serialNo);
                if (historyBySerialNo != null){
                    historyBySerialNo.getPicImgUrl();
                    if (historyBySerialNo.getPicImgUrl().equals(outImgUrl)){
                        //根据流水号删除 流水表
                        int deleteCount = flowMapper.deleteFlowBySerialNo(serialNo);
                        System.out.println("删除的结果=="+deleteCount);
                    }
                }

            }

        }

    }

    // 读取excel订单号 比较库里订单号 如果不同写入新的excel中；
    @Test
    public void testPutDataToNewExcel() throws Exception {

        //需要读取的本地的excel数据 D:\excel-poi\duizhang\source
        InputStream is = new FileInputStream("d:/excel-poi/duizhang/data/02/0218-data.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题所有内容
        List<CwParkingOrder> list = new ArrayList<>();
        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                CwParkingOrder order = new CwParkingOrder();

                String str1 = rowData.getCell(0).toString();
                String[] split1 = str1.split("\\.");
                order.setOrderNo(split1[0]);

                String amount = rowData.getCell(12).toString();
                //String[] split2 = str2.split("\\.");
                order.setAmount(amount);

                //添加商户流水
                String businessNo = rowData.getCell(7).toString();
                order.setBusinessNo(businessNo);

                String paymentTime = rowData.getCell(11).toString();
                order.setPaymentTime(paymentTime);

                list.add(order);

            }

        }
        workbook.close();

        //写入新的excel
        Workbook writeWorkbook = new SXSSFWorkbook();

        //创建一个sheet
        Sheet wrSheet = writeWorkbook.createSheet("聚合码筛选后数据");

        int rowNo = 0;
        Row row1 = wrSheet.createRow(rowNo);

        String[] titles = {"orderNo", "amount","businessNo","paymentTime"};

        for (int i = 0; i < titles.length; i++) {
            Cell cell02 = row1.createCell(i);
            cell02.setCellValue(titles[i]);
        }

        for (int i = 0; i < list.size(); i++) {

            rowNo++;
            Row row = wrSheet.createRow(rowNo);
            CwParkingOrder order = list.get(i);

            Cell orderNo = row.createCell(0);
            orderNo.setCellValue("`"+order.getOrderNo());

            Cell amount = row.createCell(1);
            amount.setCellValue(order.getAmount());

            Cell businessNo = row.createCell(2);
            businessNo.setCellValue(order.getBusinessNo());

            Cell paymentTime = row.createCell(3);
            paymentTime.setCellValue(order.getPaymentTime());

        }

        FileOutputStream out = new FileOutputStream("d:/excel-poi/duizhang/after/0218after.xlsx");
        writeWorkbook.write(out);
        writeWorkbook.close();

    }

    //银行流水和数据库中一致 但是es中的数据不一致
    //执行时 需要切换数据库配置 注意查车牌时需要换日期
    @Test
    public void testCompareData() throws Exception {
        InputStream is = new FileInputStream("C:/Users/changchen/Desktop/es中拉取的流水的信息/0216/交易流水列表_20200216.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取商品列表数据 从第一行开始读取
        int rowCount = sheet.getPhysicalNumberOfRows();

        List<String> paymentSerialNoList = cwParkingOrderMapper.getAllPaymentSerialNo();
        Set<String> carPlateNoSet = new HashSet<>();

        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                //车牌号码
                String carPlateNo = rowData.getCell(1).toString();
                carPlateNoSet.add(carPlateNo);

            }

        }

        for (String carPlate : paymentSerialNoList) {
            if (!carPlateNoSet.contains(carPlate)){
                System.out.println("es中没有的车牌 "+carPlate);
            }
        }

        workbook.close();

    }

}
