package cn.jiyun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@TableName("tb_shenpi")
@Data
@ToString
public class Approval {
    @TableId(type = IdType.AUTO, value = "id")
    private Long id;
    private String code;
    private String name;
    private Integer status;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shenpiTime;
    private String remark;
    private String orderNum;

}
