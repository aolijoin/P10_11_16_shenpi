package cn.jiyun.service.impl;

import cn.jiyun.entity.Approval;
import cn.jiyun.entity.Order;
import cn.jiyun.mapper.ApprovalMapper;
import cn.jiyun.result.PageResult;
import cn.jiyun.result.Result;
import cn.jiyun.service.ApprovalService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public  class ApprovalServiceImpl implements ApprovalService {
    @Resource
    private ApprovalMapper approvalMapper;

    @Override
    public PageResult findPage(Integer page, Integer size, Approval approval) {
        PageHelper.startPage(page, size);
        LambdaQueryWrapper<Approval> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(approval.getName())) {
            wrapper.like(Approval::getCode, approval.getName());
            wrapper.or(true);
            wrapper.like(Approval::getName, approval.getName());
        }
        List<Approval> orders = approvalMapper.selectList(wrapper);
        PageInfo info = new PageInfo(orders);

        return new PageResult(info.getTotal(), info.getList());
    }

    @Override
    public Result addApp(Approval approval) {
        try {
            approvalMapper.insert(approval);
            return Result.success();
        } catch (Exception e) {
            return Result.error("操作失败");
        }

    }

    @Override
    public Result updataApp(Approval approval) {
        try {
            approvalMapper.updateById(approval);
            return Result.success();
        } catch (Exception e) {
            return Result.error("操作失败");
        }
    }

    @Override
    public Result handleDelete(Integer id) {
        try {
            approvalMapper.deleteById(id);
            return Result.success("操作成功");
        } catch (Exception e) {
            return Result.error("操作失败");
        }
    }
}
