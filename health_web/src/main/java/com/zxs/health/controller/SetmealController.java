package com.zxs.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxs.health.Utils.QiNiuUtils;
import com.zxs.health.common.MessageConstant;
import com.zxs.health.entity.PageResult;
import com.zxs.health.entity.QueryPageBean;
import com.zxs.health.entity.Result;
import com.zxs.health.pojo.Setmeal;
import com.zxs.health.service.SetmealService;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 包名： com.zxs.health.controller
 *
 * @author: shixiaoze
 * 日期: 2020/11/23 16:47
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);

    /**
     * 图片上传
     *
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //获取上传的文件名
        String originalFilename = imgFile.getOriginalFilename();
        //截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一文件名，拼接后缀名，生成图片名
        String imgName = UUID.randomUUID().toString() + suffix;
        try {
            //调用七牛工具类进行文件上传
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), imgName);
            //成功后返回域名与图片名
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("imgName", imgName);
            resultMap.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, resultMap);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传文件失败", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    /**
     * 新建
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id) {
        Setmeal setmeal = setmealService.findById(id);
        Map resultMap = new HashMap<>();
        resultMap.put("setmeal", setmeal);
        resultMap.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, resultMap);
    }


    /**
     * 根据套餐id查询检查组id
     *
     * @param id
     * @return
     */
    @RequestMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id) {
        List<Integer> checkgroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, "查询id集合成功", checkgroupIds);
    }

    /**
     * 更新套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.update(setmeal, checkgroupIds);
        return new Result(true, "更新套餐成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(int id) {
        setmealService.delete(id);
        return new Result(true, "删除成功");
    }

}
