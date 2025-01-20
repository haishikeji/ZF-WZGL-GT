<template>
    <div>
        <z-title title="发料管理" icon="el-icon-receiving"></z-title>
        <content-card>
            <!--通用搜索-->
            <el-card>
                <div class="flex-n fac">
                    <!-- <div class="comm-label ">发料单号</div>
                    <el-input v-model="sendOrderNo" class="w-140" clearable size="mini" placeholder="请输入/扫描单号"  @keyup.enter.native="payCode" ></el-input> -->
                    <div class="comm-label ">编号</div>
                    <el-input v-model="handNo" class="w-140" clearable size="mini" placeholder="请输入录入的编号"  @keyup.enter.native="payCode" ></el-input>
                    <div class="comm-label ml-10">领料单位</div>
                    <el-select class="ele-select" clearable  size="mini" v-model="pickCompanyId" placeholder="请选择">
                            <el-option
                                    v-for="item in pickCompanyList"
                                    :key="item.id"
                                    :label="item.pickCompanyName"
                                    :value="item.id">
                            </el-option>
                    </el-select>

                    <div class="comm-label ml-10">领料人</div>
                    <el-input v-model="pickUser" class="w-140" clearable size="mini" placeholder="请输入领料人"></el-input>

                    <div class="comm-label ml-10">审核状态</div>
                    <el-select class="ele-select" clearable  size="mini"  v-model="approveStatus" placeholder="请选择">
                        <el-option
                                v-for="item in approveStatusList"
                                :key="item.codeValue"
                                :label="item.codeName"
                                :value="item.codeValue">
                        </el-option>
                    </el-select>


                    <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                    <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>
                    <el-button size="mini" type="primary" class="ml-10" @click="handleDownload('')">导出模板</el-button>
                    <el-upload
                    class="upload-demo"
                    action="#"
                    ref="upload"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :before-remove="beforeRemove"
                    multiple
                    :limit="1"
                    :on-exceed="handleExceed"
                    :file-list="fileList"
                    :http-request="handleFileUpload"
                    >
                    <el-button size="mini" type="primary" class="ml-10">导入</el-button>
                    </el-upload>
                </div>
            </el-card>
            <el-card class="box-card mt-10">
                <div slot="header" class="flex-n fac fjb">
                    <div class="comm-card-title-one">发料列表</div>
                    <div class="flex-n fac">
                        <el-button size="mini" type="primary" @click="manageIssueOrder('',0)">新增</el-button>
                        
                    </div>
                </div>
                <el-table
                        :data="tableData"
                        cell-class-name="cell_table"
                        v-loading="loading"
                        show-overflow-tooltip
                        highlight-current-row
                        :height="height+'px'"
                        element-loading-text="列表刷新中"
                        style="width: 100%">
                    <el-table-column
                            v-for="(item,index) in rowName" :key="index"
                            show-overflow-tooltip
                            :width="item.width"
                            :prop="item.prop"
                            :label="item.label"
                            :fixed="item.fixed?item.fixed:false">
                        <template slot-scope="scope">
                            <template v-if="item.prop=='approveStatus'">
                                <el-tag size="mini" v-if="scope.row.approveStatus==-1">已保存</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==0" type="info">待审核</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==1" type="warning">审核中</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==2" type="danger">审核驳回</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==3" type="success">审核成功</el-tag>

                            </template>
                            <template  v-else-if="item.prop=='todo'">
                                <el-button type="text" @click="manageIssueOrder(scope.row.id,1)">查看</el-button>
                                <el-button type="text" @click="handleDownload(scope.row.id)">导出</el-button>
                                <el-button type="text" @click="print(scope.row.tfiId,scope.row.id,'7')">打印</el-button>
                                <template v-if="scope.row.approveStatus==-1||scope.row.approveStatus==2">

                                <el-button type="text" @click="manageIssueOrder(scope.row.id,0)">编辑</el-button>
                                <el-popconfirm
                                        title="该操作将删除该数据，确定删除吗"
                                        @confirm="manageIssueOrder(scope.row.id,2)"
                                >
                                    <el-button class="ml-10" slot="reference" type="text">删除</el-button>
                                </el-popconfirm>
                                </template>
                            </template>
                            <template  v-else>
                                {{scope.row[item.prop]}}
                            </template>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                            @currentChange="handleCurrentChange"></pagination>
            </el-card>
        </content-card>
        <!--编辑发料单-->
        <issue-manage :visible.sync="editIssueOrderVisible" :id="id" @refreshList="searchList"></issue-manage>
        <!--查看发料单-->
        <issue-order-detail :visible.sync="lookIssueOrderVisible" :id="id"></issue-order-detail>
        <procedure-print :visible.sync="printVisible" :id="id" :bus-id="busId" :taskNodeId="taskNodeId" @refreshList="searchList" ></procedure-print>

    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/materialissueRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import IssueManage from "./component/IssueManage";
    import IssueOrderDetail from "./component/IssueOrderDetail";
    import ProcedurePrint from "./printhtml/ProcedurePrint";
    export default {
        components: {
            IssueOrderDetail,
            IssueManage,
            ContentCard,
            Pagination,
            ZTitle,ProcedurePrint},
        name: "material-issue",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],
                handNo: '',
                majorList:[],
                rowName:[],
                sendOrderNo:'',
                pickUser:'',
                approveStatus:'',
                pickCompanyId:'',
                pickCompanyList:[],
                approveStatusList:[],
                editIssueOrderVisible:false,
                printVisible:false,
                id:'',
                busId:'',
                taskNodeId:'',
                lookIssueOrderVisible:false,
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 380 ;
            })
            this.getApproveStatus();
            this.getPickCompanyList();
            this.searchList();
            this.getMajorList();
        },
        methods:{
            print(id,busId,taskNodeId) {
                this.id=id;
                this.busId=busId;
                this.taskNodeId=taskNodeId;
                console.log(id+"--"+busId+"--"+taskNodeId)
                this.printVisible = true;
            },
            payCode(){
                this.searchList();
            },
            
        getMajorList() {
            this.$axios.post('/major/findList', {
                "projectId": this.$root.getStorage('projectId'),
            }).then(res => {
                this.majorList = res.data.list;
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
            getApproveStatus(){
                this.approveStatusList = this.$root.getStorage('dic').filter((item) => item.codeKind == 'approveStatus');
                console.log(this.approveStatusList)
            },
            //领料单位列表
            getPickCompanyList(){
                this.$axios.post('/pickCompany/findList',{
                    "projectId":this.$root.getStorage('projectId'),
                }).then(res=>{
                    this.pickCompanyList=res.data.list;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            searchList(){
                this.currentPage=1;
                this.loading = true;
                this.getDataList();
            },
            getDataList() {
                this.$axios.post('/sendOrder/sendOrderList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "pickCompanyId":this.pickCompanyId,
                    "sendOrderNo": this.sendOrderNo,
                    "pickUser": this.pickUser,
                    "approveStatus": this.approveStatus,
                    'handNo':this.handNo,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize
                }).then(res => {
                    this.tableData = res.data.list;
                    for(var i in this.tableData){
                        // this.$set(this.tableData[i],'approveStatus',this.tableData[i].approveInfo[0].approveStatus)
                        if(this.tableData[i].approveInfo.length>0){
                             this.$set(this.tableData[i],'approveStatus',this.tableData[i].approveInfo[0].approveStatus)
                        }else{
                            this.$set(this.tableData[i],'approveStatus',"-1")
                        }
                    }
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },
            //增删改查发料单
            manageIssueOrder(id,state){
                this.id=id;
                if(state==0){
                    this.editIssueOrderVisible=true;
                }
                if(state==1){
                    this.lookIssueOrderVisible=true;
                }
               if(state==2){
                   this.$axios.post('/sendOrder/logicDeleteByIds',id).then(res=>{
                       this.$message.success('删除成功');
                       this.getDataList();
                   }).catch(err=>{
                       this.$message.error(err.msg)
                   })
               }

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
            resetData(){
                this.sendOrderNo='';
                this.approveStatus='';
                this.pickUser='';
                this.getDataList();
                this.fileUrl = '';
            },
            handleDownload(id) {
                this.$axios.post('/excel/sendOrderList', {
                    "id": id,
                }, { responseType: 'arraybuffer' }).then(res => {
                    const datas = res;
                    const url = window.URL.createObjectURL(new Blob([datas], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }))
                    const link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url;
                    if(id){
                    link.setAttribute('download', '发料管理');
                    }else{
                        link.setAttribute('download', '发料管理模板');
                    }
                    document.body.appendChild(link)
                    link.click()
                    document.body.removeChild(link);
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
            handleFileUpload(file) {
                const formData = new FormData();
                formData.append('file', file.file);
                formData.append('projectId', this.$root.getStorage('projectId'));
                this.$axios.post('/excel/uploadSendOrderData', formData).then(res=>{
                    this.$refs.upload.clearFiles();
                    this.$message.success(res.msg)
                    this.resetData()
                }).catch(err=>{
                    this.$alert(err.msg, '', {
                        cancelButtonText: '取消',
                        type: 'error'
                        })
                    // this.$message.error(err.msg)
                    this.$refs.upload.clearFiles();
                })
            },
            
        handleRemove(file, fileList) {
                this.fileUrl = ''
            },
            handlePreview(file) {
            },
            handleExceed(files, fileList) {
                this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            beforeRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },
        }
    }
</script>

<style scoped>

</style>