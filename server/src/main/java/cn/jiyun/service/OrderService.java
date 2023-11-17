package cn.jiyun.service;

import cn.jiyun.entity.Order;
import cn.jiyun.result.PageResult;
import cn.jiyun.result.Result;

public interface OrderService {
    PageResult findPage(Integer page, Integer size, Order order);

    Result addOrder(Order order);

    Result updateOrder(Order order);

    Result handleDelete(Integer id);

    Result getAll();
}
