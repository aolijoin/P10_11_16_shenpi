package cn.jiyun.service;

import cn.jiyun.entity.Approval;
import cn.jiyun.result.PageResult;
import cn.jiyun.result.Result;

public interface ApprovalService {
    PageResult findPage(Integer page, Integer size, Approval approval);

    Result addApp(Approval approval);

    Result updataApp(Approval approval);

    Result handleDelete(Integer id);
}
