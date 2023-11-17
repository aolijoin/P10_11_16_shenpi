package cn.jiyun.controller;

import cn.jiyun.entity.Order;
import cn.jiyun.result.PageResult;
import cn.jiyun.result.Result;
import cn.jiyun.service.OrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping("findPage")
    public PageResult findPage(Integer page, Integer size, @RequestBody Order order) {
        return orderService.findPage(page, size, order);
    }

    @RequestMapping("addOrder")
    public Result addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @RequestMapping("updataOrder")
    public Result updataOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }
    @RequestMapping("handleDelete")
    public Result handleDelete(Integer id) {
        return orderService.handleDelete(id);
    }
    @RequestMapping("getAll")
    public Result getAll() {
        return orderService.getAll();
    }
}
