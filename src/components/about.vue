<template>
  <div id="about">
    <div class="content-header">
      <h1>系统菜单<small>审批单管理</small></h1>
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统菜单</el-breadcrumb-item>
        <el-breadcrumb-item>审批单管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="app-container">
      <div class="box">
        <div class="filter-container">
          <el-input placeholder="审批单据名称" v-model="query.name" style="width: 200px;"
                    class="filter-item" @keydown.enter="findPage"></el-input>
          <el-button @click="findPage()">查询</el-button>
          <el-button type="primary" class="butT" @click="handleCreate">新建</el-button>
        </div>
        <el-table size="small" current-row-key="id" :data="page.data" stripe highlight-current-row>
          <el-table-column type="index" align="center" label="序号"></el-table-column>
          <el-table-column prop="code" label="审批单号" align="center"></el-table-column>
          <el-table-column prop="name" label="审批单名称" align="center"></el-table-column>
          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <span>{{
                  scope.row.status == '0' ? '审核中' : scope.row.status == '1' ? '审批通过' : '审批未通过'
                }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="type" label="单据类型" align="center"></el-table-column>
          <el-table-column prop="shenpiTime" label="审批单时间" align="center"></el-table-column>
          <el-table-column prop="remark" label="审批单说明" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination-container">
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
          <el-dialog title="新增审批单" :visible.sync="dialogFormVisible">
            <template>
              <el-tabs v-model="model" type="card">
                <el-tab-pane label="基本信息" name="first">
                  <el-form label-position="right" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="审批单编号">
                          <el-input v-model="formData.code"/>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="审批单名称">
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
                        <el-form-item label="审批单说明">
                          <el-input v-model="formData.remark" type="textarea"></el-input>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </el-form>
                </el-tab-pane>
                <el-tab-pane label="单据类型" name="second">
                  <div class="checkScrol">
                    <table class="datatable">
                      <thead>
                      <tr>
                        <th>选择</th>
                        <th>单据编码</th>
                        <th>单据名称</th>
                        <th>单据说明</th>
                      </tr>
                      </thead>
                      <tbody>
                      <!--                      <el-checkbox-group v-model="checkList" @change="onChange">-->
                      <tr v-for="c in tableData">
                        <td>
                          <input :id="c.id" v-model="checkList" type="checkbox" :value="c.id">
                          <!--                            <el-checkbox :label="c.id"> {{ c.code }} {{ c.name }} {{ c.remark }}</el-checkbox>-->
                        </td>
                        <td><label :for="c.id">{{ c.code }}</label></td>
                        <td><label :for="c.id">{{ c.name }}</label></td>
                        <td><label :for="c.id">{{ c.remark }}</label></td>
                      </tr>
                      <!--                      </el-checkbox-group>-->
                      </tbody>
                    </table>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </template>
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
  data() {
    return {
      dialogFormVisible: false,
      page: {
        page: 1,
        size: 3,
        total: 0,
        data: []
      },
      query: {
        name: ''
      },
      activeName: {}, formData: {}, tableData: [], checkList: [], model: ''
    }
  }, methods: {
    handleCurrentChange(val) {
      this.page.page = val
      this.findPage()
    }, findPage() {
      axios.post(`http://localhost:8080/app/findPage?page=${this.page.page}&size=${this.page.size}`, this.query).then(res => {
        this.page.total = res.data.total
        this.page.data = res.data.rows
      })
    }, handleCreate() {//添加page
      this.dialogFormVisible = true
      this.activeName.url = 'http://localhost:8080/app/addApp'
      this.getOrder()
      this.formData = {}
      this.checkList = []
    }, getOrder() {//获取单据
      axios.post(`http://localhost:8080/order/getAll`).then(res => {
        this.tableData = res.data.data
      })
    }, handleAdd() {//添加单据,修改
      this.formData.orderNum = this.checkList.join(',')
      console.log(this.formData)
      axios.post(this.activeName.url, this.formData).then(res => {
        let result = res.data
        if (result.code === 200) {
          this.$message.success('成功')
          this.findPage()
          this.dialogFormVisible = false
        } else {
          this.$message.error(result.msg)
        }
      })
    }, handleUpdate(row) {
      this.getOrder()
      this.dialogFormVisible = true
      this.formData = row
      console.log(row)
      this.checkList = row.orderNum.split(',')
      this.activeName.url = 'http://localhost:8080/app/updataApp'
    }, handleDelete(row) {
      axios.post(`http://localhost:8080/app/handleDelete/${row.id}/${row.name}`).then(res => {
        let result = res.data
        if (result.code === 200) {
          this.$message.success(result.data)
          this.findPage()
        } else {
          this.$message.error(result.msg)
        }
      })
    }
  }, created() {
    this.findPage()
  }
}
</script>