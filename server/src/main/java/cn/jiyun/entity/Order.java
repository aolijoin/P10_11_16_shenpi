package cn.jiyun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("oder_number")
public class Order {
    @TableId(type = IdType.AUTO, value = "id")
    private Long id;
    private String code;
    private String name;
    private String type;
    private String remark;
}
