<template>
    <div>
        <z-title title="定测量管理" icon="el-icon-finished">
            <!--跳转过来时显示-->
            <el-button size="mini" type="primary" @click="getDataList">刷新</el-button>
            <el-button size="mini" class="ml-10" @click="$router.go(-1)">返回</el-button>

        </z-title>
        <content-card>
            <!--通用搜索-->
            <el-card>
                <div class="flex-n fac">
                    <div class="comm-label ">物资编号</div>
                    <el-input v-model="goodCode" class="w-140" clearable size="mini" placeholder="请输入物资编号"></el-input>

                    <div class="comm-label ml-10">物资名称</div>
                    <el-input v-model="goodName" class="w-140" clearable size="mini" placeholder="请输入物资名称"></el-input>

                    <div class="comm-label ml-10">规格型号</div>
                    <el-input v-model="specsModel" class="w-140" clearable size="mini" placeholder="请输入规格型号"></el-input>

                    <div class="comm-label ml-10">专业名称</div>
                    <el-select class="ele-select" clearable size="mini" v-model="majorId" placeholder="请选择">
                        <el-option v-for="item in majorList" :key="item.id" :label="item.majorName" :value="item.id">
                        </el-option>
                    </el-select>


                    <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                    <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>
                    <el-button size="mini" v-if="$root.getStorage('isManage')" type="primary" class="ml-10" @click="handleDownload('')">导出模板</el-button>

                </div>
            </el-card>
            <el-card class="box-card mt-10">
                <div slot="header" class="flex-n fac fjb">
                    <div class="comm-card-title-one">定测量列表</div>
                    <div class="flex-n fac">
                        <!-- <div class="comm-label">
                            {{ approveStatus == '' ? '待提交' : '' }}
                            {{ approveStatus == '0' ? '待审核' : '' }}
                            {{ approveStatus == '1' ? '审核中' : '' }}
                            {{ approveStatus == '2' ? '审核驳回' : '' }}
                            {{ approveStatus == '3' ? '审核成功' : '' }}
                        </div> -->
                        <div class="comm-label ml-10">总计：{{ total }}</div>
                        <template v-if="approveStatus == ''">

                            <!--待审核状态四个不能弄-->
                            <el-button v-if="$root.getStorage('isManage')" size="mini" type="primary" class="ml-20" @click="manageMeasure('', 0)">新增</el-button>

                            <!-- <el-button size="mini" type="primary" class="ml-10" @click="showSubmit=true">提交</el-button> -->
                            <el-upload v-if="$root.getStorage('isManage')" class="upload-demo" action="#" ref="upload" :on-preview="handlePreview"
                                :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1"
                                :on-exceed="handleExceed" :file-list="fileList" :http-request="handleFileUpload">
                                <el-button size="mini" type="primary" class="ml-10">导入</el-button>
                            </el-upload>
                        </template>
                        <!--&lt;!&ndash;提交二次确认弹窗&ndash;&gt;-->
                        <!--<el-button size="mini" type="primary" class="ml-10" @click="importExcel">导入</el-button>-->
                        <!--<el-button size="mini" type="primary" class="ml-10">打印</el-button>-->

                    </div>
                </div>
                <el-table :data="tableData" ref="table" cell-class-name="cell_table" v-loading="loading"
                    show-overflow-tooltip highlight-current-row :height="height + 'px'" element-loading-text="列表刷新中"
                    style="width: 100%">
                    <el-table-column width="120" prop="goodCode" label="物资编号" fixed="left">
                    </el-table-column>
                    <el-table-column width="80" prop="typeName" label="类别">
                    </el-table-column>
                    <el-table-column width="120" prop="majorName" label="专业名称">
                    </el-table-column>
                    <el-table-column width="150" prop="goodName"  label="物资名称">
                    </el-table-column>
                    <el-table-column width="150" prop="specsModelName" label="规格型号">
                    </el-table-column>

                    <el-table-column width="80" prop="unit" label="单位">
                    </el-table-column>
                    <el-table-column prop="total" width="120" label="合计">
                    </el-table-column>
                    <el-table-column min-width="100" v-for="(item, index) in stationScheduleList" :key="index"
                        :prop="item.stationIntervalName" :label="item.stationIntervalName">
                    </el-table-column>
                    <el-table-column width="200" prop="remarks" label="备注">
                    </el-table-column>
                    <el-table-column width="140" label="操作" fixed="right">
                        <template slot-scope="scope">
                            <el-button type="text" @click="manageMeasure(scope.row.id, 1)">查看</el-button>
                            <el-button type="text" @click="manageMeasure(scope.row.id, 0)"
                                v-if="approveStatus == '' && $root.getStorage('isManage')">编辑</el-button>
                            <el-popconfirm title="该操作将删除该订单所有数据，确定删除当前订单吗？" @confirm="manageMeasure(scope.row.id, 2)">
                                <el-button class="ml-10" slot="reference" v-if="approveStatus == ''  && $root.getStorage('isManage')"
                                    type="text">删除</el-button>
                            </el-popconfirm>
                        </template>
                    </el-table-column>

                </el-table>
                <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                    @currentChange="handleCurrentChange"></pagination>
            </el-card>
        </content-card>
        <!--编辑定测量-->
        <measure-manage :visible.sync="editMeasureVisible" :id="id" @refreshList="searchList"></measure-manage>
        <!--查看定测量-->
        <measure-detail :visible.sync="lookMeasureVisible" :id="id"></measure-detail>
        <confirm-toast :visible.sync="showSubmit" title="该操作将提交该订单所有数据进行审核流程,确定要提交吗？"
            @submit="submitMeasureList"></confirm-toast>

    </div>
</template>

<script>
import ZTitle from "../../../components/ZTitle/ZTitle";
import rowName from './data/measureRowName';
import Pagination from "../../../components/pagination/pagination";
import ContentCard from "../../../components/ContentCard/ContentCard";
import MeasureDetail from "./component/MeasureDetail";
import MeasureManage from "./component/MeasureManage";
import ConfirmToast from "../../../components/ConfirmToast/ConfirmToast";

export default {
    components: {
        ConfirmToast,
        MeasureManage,
        MeasureDetail,
        ContentCard,
        Pagination,
        ZTitle
    },
    name: "measure",
    data() {
        return {
            isManage :false,
            majorList: [],
            approveStatus: '',
            goodCode: '',
            goodName: '',
            specsModel: '',
            majorId: '',
            pageSize: 10,
            currentPage: 1,
            total: 0,
            loading: false,
            height: 250,
            tableData: [],
            stationScheduleList: [],
            rowName: [],
            list: [],

            lookMeasureVisible: false,
            editMeasureVisible: false,
            showSubmit: false,
            id: ''
        }
    },
    mounted() {
        if( this.$root.getStorage('isManage')){
            this.isManage = true;
        }
        this.rowName = rowName;
        this.$nextTick(() => {
            this.height = document.body.offsetHeight - 320;
        })
        this.searchList();
        this.getMajorList();

    },
    methods: {
        getMajorList() {
            this.$axios.post('/major/findList', {
                "projectId": this.$root.getStorage('projectId'),
            }).then(res => {
                this.majorList = res.data.list;
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
        handleDownload() {
            this.$axios.post('/excel/exportMeasureExcel', {
                "projectId": this.$root.getStorage('projectId'),
            }, { responseType: 'arraybuffer' }).then(res => {
                const datas = res;
                const url = window.URL.createObjectURL(new Blob([datas], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }))
                const link = document.createElement('a')
                link.style.display = 'none'
                link.href = url;
                link.setAttribute('download', '定测量模板');
                document.body.appendChild(link)
                link.click()
                document.body.removeChild(link);
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },

        getState() {
            this.$axios.post('/workflow/threeEstimateStatus', {
                "busId": this.$root.getStorage('projectId') + '3'
            }).then(res => {
                console.log(res.data)
                this.approveStatus = res.data?.approveStatus || '';

            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
        searchList() {
            this.currentPage = 1;
            this.loading = true;
            this.getStationSchedule();
            this.getDataList();
            this.getState();
        },
        getDataList() {
            this.$axios.post('/threeEstimate/budgetFixedMeasureList', {
                "projectId": this.$root.getStorage('projectId'),
                "goodCode": this.goodCode,
                "goodName": this.goodName,
                "specsModel": this.specsModel,
                "majorId": this.majorId,
                pageNum: this.currentPage,
                pageSize: this.pageSize,
            }).then(res => {
                this.tableData = res.data.list;
                this.$nextTick(() => {
                    this.$refs.table.doLayout();
                })
                this.total = res.data.total;
                this.loading = false;
            }).catch(err => {
                this.$message.error(err.msg)
                this.loading = false;
            })

        },
        getStationSchedule() {
            this.$axios.get('/stationSchedule/findStationSchedule/' + this.$root.getStorage('projectId')).then(res => {
                this.stationScheduleList = res.data.list;
                this.$nextTick(() => {
                    this.$refs.table.doLayout();
                })
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
        //增删改查
        manageMeasure(id, state) {
            this.id = id;
            if (state == 0) {
                this.editMeasureVisible = true;
            }
            if (state == 1) {
                this.lookMeasureVisible = true;
            }
            if (state == 2) {
                this.$axios.post('/threeEstimate/logicDeleteByIds', id).then(res => {
                    this.$message.success('删除成功');
                    this.searchList();
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            }
        },
        //导入
        importExcel() {
            // this.$axios.post('/fixed/importExcel', {
            //     "file": '',
            // }).then(res => {
            //     this.$message.success('数据导入成功');
            // }).catch(err => {
            //     this.$message.error(err.msg)
            // })
        },
        //提交
        submitMeasureList() {
            this.$axios.post('/threeEstimate/commitFixed', {
                "projectId": this.$root.getStorage('projectId'),
            }).then(res => {
                this.$message.success('提交成功');
                this.searchList();
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.loading = true;
            this.getStationSchedule();
            this.getDataList();
            this.getMajorList();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.loading = true;
            this.getStationSchedule();
            this.getDataList();
            this.getMajorList();
            console.log(`当前页: ${val}`);
        },
        resetData() {
            this.goodCode = '';
            this.goodName = '';
            this.specsModel = '';
            this.majorId = '';
        },

        ///导入部分代码
        handleRemove(file, fileList) {
            this.fileUrl = ''
        },
        handlePreview(file) {
        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        async handleFileUpload(file) {
            this.loading = true
            const formData = new FormData();
            formData.append('file', file.file);
            formData.append('projectId', this.$root.getStorage('projectId'));
            await this.$axios.post('/excel/uploadMeasureData', formData).then(res => {
                this.$refs.upload.clearFiles();
                this.$message.success(res.msg)
                this.resetData()
                this.searchList()

            }).catch(err => {
                // this.$message.error(err.msg)
                this.$alert(err.msg, '', {
                    cancelButtonText: '取消',
                    type: 'error'
                })
                this.$refs.upload.clearFiles();
            })
            this.loading = false
        },

    }
}
</script>

<style scoped></style>