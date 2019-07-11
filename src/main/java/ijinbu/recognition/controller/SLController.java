package ijinbu.recognition.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ijinbu.recognition.utils.HttpUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/user")
public class SLController {

    //    @GetMapping("/getPicture")
//    public String getPicture(){
//        String s = null;
//
//        jsonObject.put("image","我是人脸");
//        jsonObject.put("image_type","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3543193853,2458424235&fm=27&gp=0.jpg");
//        try {
//           s = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/user/add", jsonObject.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////
//        if(file.isEmpty()){
//        return null;
//    }else
///         try {
//             if(!file1.exists())
//                 file1.mkdirs();
//             else{
//             FileOutputStream fileOutputStream  =new FileOutputStream(file1.getAbsolutePath()+"/"+file.getOriginalFilename());
//             fileOutputStream.write(file.getBytes());
//             fileOutputStream.flush();}
//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
    {
//         String url = getClass().getClassLoader().getResource("")+"/templates";
//         File file1 = new File(url.substring(5));
//         System.out.println(file1.getAbsolutePath());
//        return s;
//    }}}
    }

    /**
     * 添加人脸到人脸库
     *
     * @param file
     * @return
     */
    @PostMapping("/addFace")
    public String dealPic(@RequestParam("upload") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        String result = null;
        try {
            jsonObject.put("group", "实验小学");
            jsonObject.put("user_id", "123456789");
            jsonObject.put("user_info", "测试");
            jsonObject.put("image_index", 1);
            String s = Base64Utils.encodeToString(file.getBytes());
            System.out.println(s);
            jsonObject.put("image", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2299113196,3410006479&fm=27&gp=0.jpg");
//             jsonObject.put("image","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=805299592,2646807008&fm=15&gp=0.jpg");
            jsonObject.put("image_type", "Url");
//             jsonObject.put("image",new String(encode));
//             jsonObject.put("image_type","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=805299592,2646807008&fm=15&gp=0.jpg");
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/user/add", jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得设备推送的人脸数据
     */
    @GetMapping("/getAttendData")
    @ResponseBody
    public String getFacePushData(@RequestBody String json){
        System.out.println(json);
        return json;
    }
    /**
     * 获得分组下的人脸
     *
     * @param { "group":"qytp",
     *          "page_num":1,
     *          "page_size":1
     *          }
     * @return {"ret":0,"msg":"OK","all_count":0,"page_num":1,"page_size":1,"user_list":null}
     */
    @PostMapping("/getFaces")
    @ResponseBody
    public String listFace(@RequestBody String json) {
        String result = null;
        JSONObject o = JSONObject.parseObject(json);

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("group",o.get("group"));
//        jsonObject.put("page_num",o.get("pageNum"));
//        jsonObject.put("page_size",o.get("pageSize"));
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/user/list", o.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 删除人脸
     *
     * @param { "group":"qytp",
     *          "user_id":123456789
     *          }
     * @return
     */
    @PostMapping("/delFace")
    public String delFace(@RequestBody String json) {
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/user/delete", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据ID获取人脸详情
     *
     * @param json {
     *             "group":"qytp",
     *             "user_id":"123456789"
     *             }
     * @return {"ret":0,"msg":"OK","user_id":"123456789","user_info":"测试",
     * "image_0":"face/add59ffd52c6e9b5/fa6eced7e380246c.jpg","image_1":"","image_2":"","image_3":"","image_4":""}
     * 其中image_0的实际访问地址是IP加端口加/faceapi/res/user/在加上上面的图形地址
     */
    @PostMapping("/getFaceById")
    public String getFace(@RequestBody String json) {
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/user/info", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得所有的分组信息
     *
     * @param { "page_num":1,
     *          "page_size":1
     *          }
     * @return group_list    array(json)
     */
    @GetMapping("/getGroup")
    public String add() {
        String result = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page_num", 1);
        jsonObject.put("page_size", 2);
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/user/group", jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 人脸检测
     *
     * @param { "image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=805299592,2646807008&fm=15&gp=0.jpg",
     *          "image_type":"Url"
     *          }
     * @return {"ret":0,"msg":"ok","face_num":1,"face_list":[{"left":341,"top":73,"width":103,"height":143}]}
     */
    @PostMapping("/faceDetect")
    public String checkFace(@RequestBody String json){
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/face/detect", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        JSONArray faceList = jsonObject.getJSONArray("face_list");
//
//        for (int i = 0; i < faceList.size(); i++) {
//            System.out.println(faceList.getJSONObject(i).getString("feature"));
//        }
//        InputStream inputStream = null;
//        try {
//            URL url = new URL("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1559292290&di=9f24c49b1b2b7656eccd12ae1c5d13f5&src=http://news.21csp.com.cn/editor/UploadFile/2013-1/20130105163033093309.jpg");
//            URLConnection urlConnection = url.openConnection();
//            inputStream = urlConnection.getInputStream();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < faceList.size(); i++) {
//            JSONObject o = (JSONObject) faceList.get(i);
//            String url = getClass().getClassLoader().getResource("") + "/templates";
//            File file1 = new File(url.substring(5));
////            boolean b = cropImage(inputStream, o.getIntValue("top"), o.getIntValue("left"),
////                    o.getIntValue("width"), o.getIntValue("height"), "png", file1);
////            System.out.println(b);
//        }
        return result;
    }


    /**
     * 提取人脸特征值
     *
     * @param { "image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=805299592,2646807008&fm=15&gp=0.jpg",
     *          "image_type":"Url"
     *          "face_prop":0 (0=不需要（默认）， 1=需要返回人脸属性（性别、年龄等））
     *          }
     * @return {"ret":0,"msg":"ok","face_list":[{"left":341,"top":73,"width":103,"height":143,"feature":","sex":0,"age":0}]}
     */
    @PostMapping("/getFeature")
    public String getFeature(@RequestBody String json) {
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/face/feature", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray faceList = jsonObject.getJSONArray("face_list");

        for (int i = 0; i < faceList.size(); i++) {
            System.out.println(faceList.getJSONObject(i).getString("feature"));
        }
        return result;
    }

    /**
     * 人脸比对
     *
     * @return {"ret":0,"msg":"","match":0.9999}对比度
     * @Param 两张人脸的特征值
     */
    @GetMapping("/faceMatch")
    public String faceMatches(String json) {
        String result = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("feature_1", "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");
        jsonObject.put("feature_2", "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");
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/face/match", jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = JSONObject.parseObject(result);
        BigDecimal match = (BigDecimal) jsonObject1.get("match");
        if (match.floatValue() > 0.6) {
            return "匹配成功";
        } else
            return "匹配失败";
    }

    /**
     * 设备人脸检测
     * @param json
     *   * {
     *      *
     *      *           "image":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=672383204,2785362795&fm=58&bpow=911&bpoh=1214",
     *      *            "image_type":"Url",
     *      *            "face_prop":1,
     *      *           "group":"qytp"
     *      *
     *      * }
     * @return
    {"ret":0,"msg":"ok","face_list":{"left":34,"top":32,"width":63,"height":86,"user_id":"123456789","user_info":"测试","user_image":"/faceapi/res/user/face/add59ffd52c6e9b5/fa6eced7e380246c.jpg","match":0.7149,"sex":2,"age":22}}

     */
    @PostMapping("/device")
    public String testDiverse(@RequestBody String json) {
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/device/check", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置人脸推送地址
     * @param json
     * {
     * group	string	人脸分组
     * url_list	array(string)	推送地址列表
     * }
     * @return
     *
     */
    @PostMapping("/push/set")
    public String facePush(@RequestBody String json) {
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/push/set", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得分组下的人脸推送地址
     * @param json
     * {group}
     * @return
     * {url_list	array(string)}
     */
    @PostMapping("/push/get")
    public String getFacePush(@RequestBody String json) {
        String result = null;
        try {
            result = HttpUtils.doPost("http://192.168.1.11:18005/faceapi/v4/push/get", JSONObject.parseObject(json).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
