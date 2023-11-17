package cn.jiyun.service.impl;

import cn.jiyun.entity.Order;
import cn.jiyun.mapper.OrderMapper;
import cn.jiyun.result.PageResult;
import cn.jiyun.result.Result;
import cn.jiyun.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public PageResult findPage(Integer page, Integer size, Order order) {
        PageHelper.startPage(page, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(order.getName())) {
            wrapper.like(Order::getCode, order.getName());
            wrapper.or(true);
            wrapper.like(Order::getName, order.getName());
        }
        List<Order> orders = orderMapper.selectList(wrapper);
        PageInfo info = new PageInfo(orders);
        return new PageResult(info.getTotal(), info.getList());
    }

    @Override
    public Result addOrder(Order order) {
        try {
            orderMapper.insert(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加错误！！！");
        }
    }

    @Override
    public Result updateOrder(Order order) {
        try {
            orderMapper.updateById(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("修改错误！！！");
        }
    }

    @Override
    public Result handleDelete(Integer id) {
        try {
            orderMapper.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("修改错误！！！");
        }
    }

    @Override
    public Result getAll() {
        return Result.success(orderMapper.selectList(null));
    }
}
