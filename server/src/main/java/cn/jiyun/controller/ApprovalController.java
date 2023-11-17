package cn.jiyun.controller;

import cn.jiyun.entity.Approval;
import cn.jiyun.result.PageResult;
import cn.jiyun.result.Result;
import cn.jiyun.service.ApprovalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("app")
public class ApprovalController {
    @Resource
    private ApprovalService approvalService;

    @RequestMapping("findPage")
    public PageResult findPage(Integer page, Integer size, @RequestBody Approval approval) {
        return approvalService.findPage(page, size, approval);
    }

    @RequestMapping("addApp")
    public Result addApp(@RequestBody Approval approval) {
        approval.setShenpiTime(new Date());
        return approvalService.addApp(approval);
    }

    @RequestMapping("updataApp")
    public Result updataApp(@RequestBody Approval approval) {
        return approvalService.updataApp(approval);
    }

    @RequestMapping("handleDelete/{id}/{name}")
    public Result handleDelete(@PathVariable("id") Integer id, @PathVariable("name") String name) {
        System.out.println("ID>>>: " + id + "\n Name>>>: " + name);
        return approvalService.handleDelete(id);
    }
}
