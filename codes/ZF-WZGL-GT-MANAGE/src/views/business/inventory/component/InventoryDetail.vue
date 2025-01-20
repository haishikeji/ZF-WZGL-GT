<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1600px" 
            
    >
        <div slot="title" class="comm-toast-title-one">盘点单详情</div>

        <el-card shadow="never" >
            <div class="flex-n fac">
                    <div class="comm-label ">物资名称</div>
                    <el-input v-model="goodName" class="w-140" clearable size="mini" placeholder="请输入物资名称" ></el-input>

                    <div class="comm-label ml-10">专业名称</div>
                        <el-select class="ele-select" clearable size="mini" v-model="majorId" placeholder="请选择">
                            <el-option v-for="item in majorList" :key="item.id" :label="item.majorName" :value="item.id">
                            </el-option>
                        </el-select>
                    <el-button size="mini" type="primary" class="ml-10" @click="getDataList">查询</el-button>
                    <el-button size="mini" class="ml-10" @click="handleDownload">导出</el-button>

                </div>
            <div class="info-area" style=" padding-top: 20px;">
                <div class="text">盘点单号</div>
                <div class="num">{{info.checkNo}}</div>
                <div class="text">盘点日期</div>
                <div class="num">{{info.checkTime}}</div>
                <div class="text">盘点仓库</div>
                <div class="num">{{info.storeName}}</div>
                <div class="text">盘点人</div>
                <div class="num">{{info.createUserName}}</div>
                <div class="text">创建时间</div>
                <div class="num">{{info.createTime}}</div>
                <div class="text">盈亏数量</div>
                <div class="num">{{info.resultNum}}</div>
                <div class="text">审核状态</div>
                <div class="num">
                    {{info.approveStatus==-1?'已保存':''}}
                    {{info.approveStatus==0?'待审核':''}}
                    {{info.approveStatus==1?'审核中':''}}
                    {{info.approveStatus==2?'审核驳回':''}}
                    {{info.approveStatus==3?'审核成功':''}}
                </div>
                <div class="text">审核原因</div>
                <div class="num ">{{info.reason}}</div>
                <div class="text">备注</div>
                <div class="num remark">{{info.remarks}}</div>
            </div>

            <el-table
                    class="mt-20"
                    :data="tableData"
                    cell-class-name="cell_table"
                    v-loading="loading"
                    highlight-current-row
                    :height="height+'px'"
                    element-loading-text="列表刷新中"
                    style="width: 100% ;">
                <el-table-column 
                        v-for="(item,index) in rowName" :key="index"
                        :width="item.width"
                        :prop="item.prop"
                        :label="item.label"
                        :fixed="item.fixed?item.fixed:false" >

                        <template slot-scope="scope">
                                <dev style="white-space: pre-wrap;  word-break: break-word; font-size: 12px;">{{ scope.row[item.prop] }}</dev>
                        </template>
                </el-table-column>
            </el-table>
            <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                            @currentChange="handleCurrentChange"></pagination>
        </el-card>
    </el-dialog>
</template>

<script>
    import Pagination from "../../../../components/pagination/pagination";
    export default {
        components: {
            Pagination},
        name: "inventory-detail",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                loading: false,
                height: 350,
                tableData: [],
                majorList:[],
                pageSize:20,
                currentPage: 1,
                goodName:'',
                majorId:'',
                total: 0,
                rowName: [
                    {
                        label: '物资编号',
                        prop: 'goodCode',
                        width: '',
                        fixed: ''
                    }, {
                        label: '物资名称',
                        prop: 'goodName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '专业名称',
                        prop: 'majorName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '规格型号',
                        prop: 'specsModelName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '计量单位',
                        prop: 'measureUnit',
                        width: '',
                        fixed: ''
                    }, {
                        label: '库位',
                        prop: 'gridName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '库存数量',
                        prop: 'stockNum',
                        width: '',
                        fixed: ''
                    }, {
                        label: '盘点数量',
                        prop: 'num',
                        width: '',
                        fixed: ''
                    }, {
                        label: '盈亏数量',
                        prop: 'resultNum',
                        width: '',
                        fixed: ''
                    }],
                info:{}
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                        this.getData();
                        this.getMajorList();
                }
            }
        },
        methods: {
        getMajorList() {
            this.$axios.post('/major/findList', {
            "projectId":this.$root.getStorage('projectId'),
            }).then(res => {
                this.majorList = res.data.list;
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
            getData(){
                this.$axios.get('/check/findOnlyById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    if(res.data.approveInfo.length>0){
                        this.$set(this.info,'approveStatus',res.data.approveInfo[0].approveStatus);
                        this.$set(this.info,'reason',res.data.approveInfo[0].remarks);
                    }else{
                        this.$set(this.info,'approveStatus',"-1");
                    }
                       
                        this.getDataList()
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.$emit('update:visible', false);
                this.tableData= [],
               this. this.majorList=[],
               this.pageSize=20,
               this.currentPage=1,
               this.goodName='',
               this. majorId=''
            },
        
        getDataList(){
            this.$axios.post('/checkDetail/findAll', {
                "checkId": this.info.id,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                goodName: this.goodName,
                majorId: this.majorId,
            }).then(res => {
                this.tableData = res.data.list;
                this.total = res.data.total;
            }).catch(err => {
                this.$message.error(err.msg)
            })
            this.loading = false;
            },
            handleSizeChange(val) {
            this.pageSize = val;
            this.loading = true;
            this.getDataList();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.loading = true;
            this.getDataList();
            console.log(`当前页: ${val}`);
        },
        handleDownload() {
                this.$axios.post('/excel/inventoryList', {
                    "id": this.info.id,
                    "goodName": this.goodName,
                    "majorId": this.majorId,
                }, { responseType: 'arraybuffer' }).then(res => {
                    const datas = res;
                    const url = window.URL.createObjectURL(new Blob([datas], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }))
                    const link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url;
                    link.setAttribute('download', '盘点管理');
                    document.body.appendChild(link)
                    link.click()
                    document.body.removeChild(link);
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
        }
    }
</script>

<style scoped lang="scss">
    .info-area {
        display: grid;
        width: 100%;
        grid-row-gap: 10px;
        grid-template-columns: repeat(4, 10% 12%);
        font-size: 14px;
        .text {
            text-align: right;
            margin-right: 20px;
        }
        .num {
            text-align: left;
            padding:0 10px;
            background: #f5f7fa;
            color: #c6c6c6;
        }
    }
</style>