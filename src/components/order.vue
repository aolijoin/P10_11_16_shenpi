<template>
  <div id="order">
    <div class="content-header">
      <h1>系统菜单<small>单据管理</small></h1>
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统菜单</el-breadcrumb-item>
        <el-breadcrumb-item>单据管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="app-container">
      <div class="box">
        <div class="filter-container">
          <el-input placeholder="单据编号/单据名称" v-model="query.name" style="width: 200px;"
                    class="filter-item"></el-input>
          <el-button @click="findPage()" class="dalfBut">查询</el-button>
          <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
        </div>
        <el-table size="small" current-row-key="id" :data="page.data" stripe highlight-current-row>
          <el-table-column type="index" align="center" label="序号"></el-table-column>
          <el-table-column prop="code" label="单据编号" align="center"></el-table-column>
          <el-table-column prop="name" label="单据名称" align="center"></el-table-column>
          <el-table-column prop="type" label="单据类型" align="center"></el-table-column>
          <el-table-column prop="remark" label="单据说明" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination-container">
          <!--分页组件
          @current-change 切换页码的时候，触发的函数handleCurrentChange
          -->
          <el-pagination
              class="pagiantion"
              @current-change="handleCurrentChange"
              :current-page="page.page"
              :page-size="page.size"
              layout="total, prev, pager, next, jumper"
              :total="page.total">
          </el-pagination>
        </div>
        <!-- 新增标签弹层 -->
        <div class="add-form">
          <el-dialog title="新增单据" :visible.sync="dialogFormVisible">
            <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单据编号" prop="code">
                    <el-input v-model="formData.code"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="单据名称" prop="name">
                    <el-input v-model="formData.name"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单据类型">
                    <el-select v-model="formData.type">
                      <el-option label="物料出库" value="物料出库"></el-option>
                      <el-option label="物料入库" value="物料入库"></el-option>
                      <el-option label="报销单据" value="报销单据"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="24">
                  <el-form-item label="单据说明">
                    <el-input v-model="formData.remark" type="textarea"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取消</el-button>
              <el-button type="primary" @click="handleAdd()">确定</el-button>
            </div>
          </el-dialog>
        </div>


      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: 'order',
  data() {
    return {
      page: {
        page: 1,
        size: 3,
        total: 0,
        data: []
      },
      query: {
        name: ''
      },
      formData: {},
      dialogFormVisible: false,
      model: {}
    }
  }, methods: {
    findPage() {
      axios.post(`http://localhost:8080/order/findPage?page=${this.page.page}&size=${this.page.size}`, this.query).then(res => {
        let result = res.data
        this.page.total = result.total
        this.page.data = result.rows
      })
    }, handleCurrentChange(val) {
      this.page.page = val
      this.findPage()
    }, handleCreate() {
      this.dialogFormVisible = true
      this.model.url = 'http://localhost:8080/order/addOrder'
    }, handleAdd() {
      axios.post(this.model.url, this.formData).then(res => {
        let result = res.data
        if (result.code === 200) {
          this.dialogFormVisible = false
          this.findPage()
        } else {
          this.$message.error(result.msg)
        }
      })
    }, handleUpdate(row) {
      this.model.url = 'http://localhost:8080/order/updataOrder'
      this.formData = row
      this.dialogFormVisible = true
    },handleDelete(row){
      this.$confirm("你确定要删除当前数据吗？","提示",{//确认框
        type:'warning'
      }).then(()=>{
        //用户点击确定按钮，发送ajax请求，将课程ID提交到Controller进行处理
        axios.get("http://localhost:8080/order/handleDelete?id=" + row.id).then((res) => { //点击确定执行的操作
          if(res.data.code===200){
            //执行成功
            //弹出成功提示信息
            this.$message.success("成功")
            //重新进行分页查询
            this.findPage();
          }else{
            //执行失败
            this.$message.error(res.data.msg);
          }
        });
      }).catch(()=>{ //点击取消执行的操作
        this.$message({
          type:'info',
          message:'操作已取消'
        });
      });
    }


  }, created() {
    this.findPage();//VUE对象初始化完成后调用分页查询方法，完成分页查询
  }
}
</script>
<style>

</style>