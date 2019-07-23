package ijinbu.recognition.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ijinbu.recognition.utils.HttpUtil;
import ijinbu.recognition.utils.SHA256Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/use")
public class HkController {
    @Value("aa")
    private String name;
    private String url = "http://192.168.1.108";
    private static final String appKey = "app34ZLAF6";
    private static final String appKeyValue = "a1e300b6324ef69e39b68bed450c541043d3a2cddd6c846987dc5f9f192657d1";
    private static final String GET_CLASSROOMS = "/eop/services/common/get/roomTreeEx";
    private static final String ADD_CLASSROOM = "/eop/services/common/post/addClassroom";
    private static final String UPDATE_CLASSROOM = "/eop/services/common/post/updateClassroom";
    private static final String DEL_CLASSROOM = "/eop/services/common/post/deleteClassroom";

    private static final String GET_CLASS_DEVICES = "/eop/services/common/get/deviceChannelEx";
    private static final String ADD_CLASS_DEVICE = "/eop/services/common/post/relateDeviceChannel";
    private static final String DEL_CLASS_DEVICE = "/eop/services/common/post/cancelDeviceChannel";

    private static final String GET_SCHOOL = "/eop/services/common/get/orgTreeEx";//获取学校组织树
    private static final String ADD_GRADE = "/eop/services/common/post/addGradeOrg";

    private static final String UPDATE_GRADE = "/eop/services/common/post/updateGradeOrg";
    private static final String DEL_GRADE = "/eop/services/common/post/deleteGradeOrg";

    private static final String ADD_OR_UPDATE_CLASS = "/eop/services/common/post/addOrUpdateClass";
    private static final String DEL_CLASS = "/eop/services/common/post/deleteSchoolClass";
    //private static final String GET_ = "";

    private static final String ADD_OR_UPDATE_STUDENT = "/eop/services/common/post/addOrUpdateStudent";
    private static final String DEL_STUDENT = "/eop/services/common/post/deleteStudent";
    private static final String GET_STUDENT = "/eop/services/common/get/studentListEx";


    private static final String GET_STUDENT_ATTEND = "/eop/services/common/get/studentAttendData";

    private static final String ADD_OR_UPDATE_TEACHER = "/eop/services/common/post/addOrUpdateTeacher";
    private static final String DEL_TEACHER = "/eop/services/common/post/deleteTeacher";
    private static final String GET_TEACHERS = "/eop/services/common/get/teacherListEx";

    private static final String ADD_OR_UPDATE_OPT_CLASS = "/eop/services/common/post/addOrUpdateOptClass";
    private static final String DEL_OPT_CLASS  = "/eop/services/common/post/deleteOptClass";
    private static final String GET_SCHEDULE = "/eop/services/common/get/courseListByTimeEx";

    /**
     * 获得教室组织树
     * @param orgCode 组织节点编码(选填)
     * @param level 查询的级别树，不小于0(选填，默认 100)
     * @return orgCode String 组织编码
     *  orgName String 组织名称
     * parentCode String 父级节点
     * phyOrgDefine Integer 物理组织定义，0-区县 1-学校，8-普通组织 9-教室组织
     * Children[] Object 子节点,数据接口和当前节点相同
     * interactionType Integer 教室互动类型 0-无互动1-私有互动 2-公有互动
     */
    @ResponseBody
    @RequestMapping(value = "/getClassRoomList", method = RequestMethod.GET)
    public String getRoomList(String orgCode, Integer level) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orgCode", orgCode);
        parameters.put("level", level);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_CLASSROOMS, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加教室或组织
     * @param interactionType 选填 教室互动类型 0-无互动 1-私有互动 2-公有互动
     * @param orgName 组织名称
     * @param parentCode 父级节点，默认组织节点：00000001
     * @param phyOrgDefine 物理组织定义，8-普通组织 9-教室组织
     * @return {
     * "code": "0",
     * "version": null,
     * "msg": "操作成功",
     * "data": {
     * "orgCode": "0000008",
     * "orgName": "教室测试 1",
     * "phyOrgDefine": 9,
     * "interactionType": 0,
     * "sort": 120,
     * "parentCode": "00000001",
     * "deviceChannelVo": null
     * } }
     */
    @ResponseBody
    @RequestMapping(value = "/addClassRoom", method = RequestMethod.POST)
    public String addClassroom(Integer interactionType,String orgName,String parentCode,Integer phyOrgDefine) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("interactionType", interactionType);
        parameters.put("orgName", orgName);
        parameters.put("parentCode", parentCode);
        parameters.put("phyOrgDefine", phyOrgDefine);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_CLASSROOM, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更改教室
     * @param interactionType 教室互动类型
     * @param orgName 组织名称
     * @param orgCode 组织代码
     * @return {"code": "0", "version": null, "msg":"操作成功", "data": null}
     */
    @ResponseBody
    @RequestMapping(value = "/updateClassRoom", method = RequestMethod.POST)
    public String updateClassroom(Integer interactionType,String orgName,String orgCode) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("interactionType", interactionType);
        parameters.put("orgName", orgName);
        parameters.put("orgCode", orgCode);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + UPDATE_CLASSROOM, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除教室或组织
     * @param orgCodeList 教室编号列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteClassRoom", method = RequestMethod.POST)
    public String deleteClassroom(@RequestParam("orgCodeList") List<String> orgCodeList) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orgCodeList", orgCodeList);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + DEL_CLASSROOM, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取教室的设备列表
     *
     * @param channelIndexCode 选填 设备通道编号
     * @param roomOrgCode      选填 教室编码
     * @param channelNameLik   选填 设备通道名称模糊查询
     * @param page             页码数
     * @param size             每页展示数
     * @return
     * cameraType integer 监控点类型 0-枪机;1-半球;2-快球
     * channelId string 通道 id
     * data 数据>
     * channelIndexCo string 通道索引号
     * de
     * channelName string 通道名称
     * channelNo integer 通道号
     * channelType string 通道类型
     * clazzroomCode string 通道关联教室
     * clazzroomName string 通道关联教室名称
     * deviceFrom integer 设备来源 1-( 教 务)6.x 2-录播组件
     * deviceIndexCod string 设备索引号
     * deviceName string 设备名称
     * deviceType integer 设备类型，2-网络摄像机 16-教学评估一体机 161-超脑设备 17-4k 深眸系列 2221-明眸设备 500-录播主机
     *                    501- 云 录 播 盒 子502-互动录播主机
     * ip string 通道 ip
     * port integer 通道端口
     * status integer 通道状态 0-正常负值-删除
     * streamType integer 码流类型 0-主码流1-子码流 2-3G 码 流
     * viewAngle integer 通道视角，1-老师2-学生
     */
    @RequestMapping(value = "/getRoomDev", method = RequestMethod.GET)
    @ResponseBody
    public String getRoomDev(String channelIndexCode, String roomOrgCode, String channelNameLik, Integer page, Integer size
    ) {

        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("channelIndexCode", channelIndexCode);
        parameters.put("roomOrgCode", roomOrgCode);
        parameters.put("channelNameLik", channelNameLik);
        parameters.put("page", page);
        parameters.put("size", size);


        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_CLASS_DEVICES, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 设备关联教室
     * @param channelIdList 设备通道编号列表
     * @param classRoomCode 教室
     * @param viewAngle     选填 选填，通道关联视角（2-学生、1-老师,22-学生全
     *                      局，23-学生旋转）
     * @return {"code":"0","version":null,"msg":"操作成功","data":null}
     */
    @RequestMapping(value = "/devReaClassRoom", method = RequestMethod.POST)
    @ResponseBody
    public String DevReaClassRoom(List<String> channelIdList, String classRoomCode, Integer viewAngle) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("channelIdList", channelIdList);
        parameters.put("classRoomCode", classRoomCode);
        parameters.put("viewAngle", viewAngle);
        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_CLASS_DEVICE, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 取消设备教室关联
     * @param channelIdList  List<String> 关联设备通道编号列表
     * @return {"code":"0","version":null,"msg":"操作成功","data":null}
     */
     @RequestMapping(value = "/cancelDevReaClassRoom", method = RequestMethod.POST)
    @ResponseBody
    public String cancelDevClassRoom(List<String> channelIdList) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("channelIdList", channelIdList);


        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + DEL_CLASS_DEVICE, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得教室组织树
     * @param orgCode String 组织节点编码（必填）根组织为“000000”
     * @return orgCode String 组织代码，根组织为000000”，基础配置中的其他接口使用的orgCode 都来于此
     * orgDefine String 组织级别定义 0-区县1-学校 2-校区 3-学院 4- 系5-专业6-年级7-班级
     * orgName String 组织名称
     * parentCode String 父节点代码
     * gradeId Long 年级 Id（只有年级节点有值）
     * orgSubTree List<date> 子节点信息
     * @deprecated 按节点查询学校组织树, 可以查询学校列表、校区列表、学院列表、系列表、专业
     * 列表、年级列表、班级列表
     */
    @ResponseBody
    @RequestMapping(value = "/getSchoolList", method = RequestMethod.GET)
    public Object getSchoolList(String orgCode) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orgCode", orgCode);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_SCHOOL, parameters, getHeaders());
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONObject data = JSONObject.parseObject(jsonObject.getString("data"));
            JSONArray jsonArray = JSONArray.parseArray(data.getString("orgSubTree"));
            JSONArray orgCode1 = (JSONArray)jsonArray.getJSONObject(0).get("orgSubTree");
            return orgCode1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加年级
     * @param gradeName 年级名称
     * @param parentOrgCodes 父组织节点编号列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addGrade", method = RequestMethod.POST)
    public String addGrade(String gradeName, String parentOrgCodes) {

        List<String> data = new ArrayList<>();
        data.add(parentOrgCodes);
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("gradeName", gradeName);
        parameters.put("parentOrgCodes", data);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_GRADE, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更改年级
     * @param gradeId 年级编号
     * @param gradeName 年级名称
     * @param updateParentOrgCodes 父节点组织名称
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateGrade", method = RequestMethod.POST)
    public String updateGrade(String gradeId,String gradeName,List<String> updateParentOrgCodes) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("gradeId", gradeId);
        parameters.put("gradeName", gradeName);
        parameters.put("updateParentOrgCodes", updateParentOrgCodes);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + UPDATE_GRADE, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除年级
     * @param gradeIds 年级编号列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteGrade", method = RequestMethod.POST)
    public  String deleteGrade(List<Long> gradeIds) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("gradeIds", gradeIds);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + DEL_GRADE, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加/编辑行政班
     * @param clazzName 班级名称
     * @param orgCode 编辑操作必填，班级编号
     * @param clazzRoomOrgCode 选填，教室编号
     * @param gradeOrgCode 必填，年级编号
     * @param headerTeacherId 选填，教师编号
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/addClass", method = RequestMethod.POST)
    public String addClass(String clazzName, String orgCode, String clazzRoomOrgCode, String gradeOrgCode, String headerTeacherId
    ) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clazzName", clazzName);
        parameters.put("orgCode", orgCode);
        parameters.put("clazzRoomOrgCode", clazzRoomOrgCode);
        parameters.put("gradeOrgCode", gradeOrgCode);
        parameters.put("headerTeacherId", headerTeacherId);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_OR_UPDATE_CLASS, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除学校班级
     * @param clazzOrgCodes  必填，班级编号列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteClass", method = RequestMethod.POST)
    public String deleteClass(List<String> clazzOrgCodes) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("gradeIds", clazzOrgCodes);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + DEL_CLASS, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加/编辑选修班
     * @param optionalClassName 班级名称
     * @param optionalClassCode 编辑操作必填，班级编号
     * @param parentOrgCode 班级所属组织
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addOptClass", method = RequestMethod.POST)
    public String addOptClass(String optionalClassName, String optionalClassCode, String parentOrgCode
    ) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("optionalClassName", optionalClassName);
        parameters.put("optionalClassCode", optionalClassCode);
        parameters.put("parentOrgCode", parentOrgCode);
        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_OR_UPDATE_OPT_CLASS, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除学校班级
     * @param classIds  必填，班级编号列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteOptClass", method = RequestMethod.POST)
    public String deleteOptClass(List<String> classIds) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("classIds", classIds);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + DEL_OPT_CLASS, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取教师/学生/班级课表列表
     * @param schoolOrgCode 必填，学校编码
     * @param startDate 必填，开始日期 yyyy-MM-dd HH:mm
     * @param endDate 必填，结束日期 yyyy-MM-dd HH:mm
     * @param personNo 选填，人员编号（教师/学生）
     * @param classCode 选填，班级编号
     * @param subjectId 选填，学科编号
     * @param page 必填，页码默认1
     * @param size 必填，每页展示数目 默认200
     * @return
     * courseId Long 课程 id
     * weekIndex Integer 星期
     * sectionIndex Integer 节次
     * sectionName String 节次名称
     * roomCode String 教室编码
     * roomName String 教室名称
     * personNo String 教师编码
     * teacherName String 教师名称
     * subjectId Long 学科 id
     * subjectNo String 学科编号
     * data
     * subjectName String 学科名称
     * gradeId Long 年级 id
     * gradeName String 年级名称
     * classCode String 班级编码
     * className String 班级名称
     * startDate String 开始时间
     * endDate String 结束时间
     * startWeek Integer 开始周次
     * endWeek Integer 结束周次
     * courseType Integer 课程类型：0-行政班，
     * weekType Integer 单双周类型(0:全周,1:单周,2:双周)
     * @deprecated 按条件查询课表信息 排序：按开始时间（startDate）、教师编码（personNo）正序
     */
    @ResponseBody
    @RequestMapping(value = "/getCourseListByTimeEx", method = RequestMethod.GET)
    public String getCourseListByTimeEx(String schoolOrgCode,String startDate,String endDate,String personNo,String classCode, Long subjectId, Integer page, Integer size) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("schoolOrgCode", schoolOrgCode);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("personNo", personNo);
        parameters.put("classCode", classCode);
        parameters.put("subjectId", subjectId);
        parameters.put("page", page);
        parameters.put("size", size);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_SCHEDULE, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取学生列表
     * @param orgCodeList 必填，组织节点
     * @param name 选填，人员姓名模糊查询
     * @param type 必填，查询方式，true 为查询当前节点及其子节点下人员，false 为只查询当前节点下的人员
     * @param personNo 选 填 ， 人 员 编号,1-64 位数字、字母或'-'组合，精确查请求参数 询
     * @param needImage 选填，true-获取图片，false-不获取图片
     * @param personNoLike 选填,人员编号,1-64位数字、字母或'-'组合，模糊查询，personNo 与personNoLike 同时填写时以personNoLike 为准
     * @param page 页码
     * @param size 每页数量
     * @return
     * name String 人员姓名
     * personNo String 人员编号
     * gender Integer 性别,1 为男，2 为女
     * gradeCode String 年级编号
     * gradeName String 年级名称
     * cardNum String 卡号
     * personPictureInfoVoList List<String> 学生照片
     * orgCode String 组织节点编号
     * orgName String 组织节点名称
     */
    @ResponseBody
    @RequestMapping(value = "/getStudentList", method = RequestMethod.GET)
    public String getStudentList(String orgCodeList,String name,String type,String personNo,String needImage, Long personNoLike, Integer page, Integer size) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orgCodeList", orgCodeList);
        parameters.put("name", name);
        parameters.put("type", type);
        parameters.put("personNo", personNo);
        parameters.put("needImage", needImage);
        parameters.put("personNoLike", personNoLike);
        parameters.put("page", page);
        parameters.put("size", size);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_STUDENT, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得学生考勤数据
     * @param page 页码，默认为 1
     * @param size 每页大小，默认为 2000
     * @param startTime 选 填 ， [YYYY-MM-DD hh:MM:ss]
     * @param endTime 选 填 ， [YYYY-MM-DD hh:MM:ss]
     * @param teacherNo 选填，教师职工号，精准
     * @param teacherName 选填，教师姓名，模糊匹
     * @param subjectId 选填，科目 code，精准匹
     * @param subjectName 选填，科目 名称，精准匹
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStudentAttendData", method = RequestMethod.GET)
    public String getStudentAttendData(Integer page,Integer size,String startTime,String endTime,String teacherNo,String teacherName,String subjectId,String subjectName) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", page);
        parameters.put("size", size);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("teacherNo", teacherNo);
        parameters.put("teacherName", teacherName);
        parameters.put("subjectId", subjectId);
        parameters.put("subjectName", subjectName);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_STUDENT_ATTEND, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加或修改学生
     * @param personNo 必填，人员编号
     * @param name 必填，姓名
     * @param gender 必填，性别：1-男，2-女
     * @param orgCode 必填，所属班级
     * @param whetherAccountCreate 必填，是否创建用户：true-创建，false-不创建
     * @param cardNum 选填，卡号，必须为字母或数字
     * @return
     */
    @PostMapping(value = "/addOrUpdateStudent")
    @ResponseBody
    public String addStudents(String personNo, String name, String gender, String orgCode,boolean whetherAccountCreate,String picturePath,String cardNum) {

        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("personNo", personNo);
        parameters.put("name", name);
        parameters.put("gender", gender);
        parameters.put("orgCode", orgCode);
        parameters.put("whetherAccountCreate", whetherAccountCreate);
        List<String> data = new ArrayList<>();
        data.add(picturePath);
        parameters.put("picturePath", data);
//        parameters.put("cardNum", cardNum);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_OR_UPDATE_STUDENT, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除学生
     * @param personNoList 人员编号列表
     * @return
     */
    @PostMapping(value = "/deleteStudent")
    @ResponseBody
    public String deleteStudents(List<String> personNoList) {

        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("personNoList", personNoList);


        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + DEL_STUDENT, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得教师列表
     * @param orgCodeList 必填，组织节点
     * @param name 选填，人员姓名模糊查询
     * @param type 必填，查询方式，true 为查询当前节点及其子节点下人员，false 为只查询当前节点下的人员
     * @param personNo 选 填 ， 人 员 编号,1-64 位数字、字母或'-'组合，精确查请求参数 询
     * @param personNoLike 选填,人员编号,1-64位数字、字母或'-'组合，模糊查询，personNo 与personNoLike 同时填写时以personNoLike 为准
     * @param page 页码
     * @param size 每页数量
     * @return
     * name String 人员姓名
     * personNo String 人员编号
     *  gender Integer 性别,1 为男，2 为女
     * teacherType Integer 教师类型，0为班主任，1 为任课教师
     * orgCode String 组织节点编号
     * orgName String 组织节点名称
     * famous String 名师标志，1 为名师，0 为普通教师
     */
     @ResponseBody
    @RequestMapping(value = "/getTeacherList", method = RequestMethod.GET)
    public String getTeacherList(String orgCodeList,String name,String type,String personNo, Long personNoLike, Integer page, Integer size) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orgCodeList", orgCodeList);
        parameters.put("name", name);
        parameters.put("type", type);
        parameters.put("personNo", personNo);
        parameters.put("personNoLike", personNoLike);
        parameters.put("page", page);
        parameters.put("size", size);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendGetRequest(url + GET_TEACHERS, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加或修改教师
     * @param personNo 必填，人员编号
     * @param name 必填，姓名
     * @param gender 必填，性别：1-男，2-女
     * @param orgCode 必填，所属班级
     * @param whetherAccountCreate 必填，是否创建用户：true-创建，false-不创建
     * @param //picturePath 选填，kms 图片信息
     * @param headerTeacher 必填 是否班主任
     * @param manageOrgCode 必填 行政组织
     * @param roles 必填 用户角色
     * @param //pictureNameList 选填 图片名称
     * @return
     */
    @PostMapping(value = "/addOrUpdateTeacher")
    @ResponseBody
    public String addTeacherOrUpdate(String personNo, String name, String gender, String orgCode,boolean whetherAccountCreate,String manageOrgCode, Boolean headerTeacher,
                                  String roles) {

        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("personNo", personNo);
        parameters.put("name", name);
        parameters.put("gender", gender);
        parameters.put("orgCode", orgCode);
        parameters.put("whetherAccountCreate", whetherAccountCreate);
//        parameters.put("picturePath", picturePath);
        parameters.put("manageOrgCode", manageOrgCode);
        parameters.put("headerTeacher", headerTeacher);
        parameters.put("roles", roles);
//        parameters.put("pictureNameList", pictureNameList);

        System.out.println("海康接收参数：" + parameters);
        try {
            String json = HttpUtil.sendPostRequest(url + ADD_OR_UPDATE_TEACHER, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除教师
     * @param personList 人员编号列表
     * @return
     */
    @PostMapping(value = "/deleteTeacher")
    @ResponseBody
    public String deleteTeachers(List<String> personList) {
        //请求参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("personList", personList);
        System.out.println("海康接收参数：" + parameters);
        try {
                String json = HttpUtil.sendPostRequest(url + DEL_TEACHER, parameters, getHeaders());
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }








    private Map<String, String> getHeaders() {
        //请求头信息
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Cache-Control", "no-cache");
        headers.put("appKey", appKey);
        Long appSecret = Calendar.getInstance().getTimeInMillis();
        headers.put("appSecret", appSecret + "");
        String wjwAuthorization = SHA256Util.getSHA256StrJava(appKey + appKeyValue + appSecret);
        headers.put("wjwAuthorization", wjwAuthorization);
        return headers;
    }

    public static void main(String[] args) {
//        Long dstr = Calendar.getInstance().getTimeInMillis();
//        String wjwAuthorization = SHA256Util.getSHA256StrJava(HkController.appKey + HkController.appKeyValue + dstr);
//        System.out.println(dstr);
//        System.out.println(wjwAuthorization);
        HkController hkController = new HkController();
        List<Long> grade = new ArrayList<>();
        for (Long i = 23L; i < 29L; i++) {
            grade.add(i);
        }
        String json = hkController.deleteGrade(grade);
        System.out.println(json);
}}
