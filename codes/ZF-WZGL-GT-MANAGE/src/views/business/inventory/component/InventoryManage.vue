<template>
    <div>
        <el-dialog
                class="order-manage"
                :visible.sync="visible"
                :show-close="false"
                fullscreen
                center
        >
            <z-title slot="title" :title="(id==''?'新增':'编辑')+'盘点单'" icon="el-icon-receiving" class="common-fixed-title">
                <el-button size="medium" @click="resetForm">返回</el-button>
            </z-title>

            <el-card shadow="never" class="mt-10" >
                <el-form :model="info" class="order_form" :rules="rules" size="mini" ref="ruleForm" label-width="120px"
                         :show-message="false">
                    <div class="flex-n" >
                        <el-form-item label="仓库名称" prop="storeId">
                            <el-select class="ele-select" clearable   v-model="info.storeId" placeholder="请选择">
                                <el-option
                                        v-for="item in storeList"
                                        :key="item.id"
                                        :label="item.storeName"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="专业名称" prop="majorId">
                            <el-select class="ele-select" clearable size="mini" v-model="info.majorId" placeholder="请选择">
                                <el-option v-for="item in majorList" :key="item.id" :label="item.majorName" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="盘点日期" prop="checkTime">
                            <el-date-picker
                                    v-model="info.checkTime"
                                    type="date"
                                    :disabled="id==''?false:true"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </div>
                        <el-form-item label="备注" prop="remarks">
                            <el-input
                                    type="textarea"
                                    maxlength="256"
                                    :rows="5"
                                    show-word-limit
                                    placeholder="请输入备注信息"
                                    v-model="info.remarks">
                            </el-input>
                        </el-form-item>
                </el-form>
            </el-card>
            <!--物资信息列表-->
            <el-card shadow="never" class="box-card mt-10">
                <div slot="header" class="flex-n fac fjb">
                    <div class="comm-card-title-one">物资信息</div>
                    <div class="flex-n fac">
                        <el-button size="mini" type="primary" @click="addMaterial">增加盘点物资</el-button>
                        <material-list :visible.sync="editMaterial" @getMaterial="getMaterial" :data="tableData"></material-list>
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
                            <template  v-if="item.prop=='num'">
                                <el-input-number class="w-90" v-model="scope.row.num" size="mini" :controls="false"  :min="1"></el-input-number>
                            </template>
                            <template  v-else-if="item.prop=='resultNum'">
                                {{Number(scope.row.num||0)-Number(scope.row.stockNum||0)}}
                            </template>
                            <template  v-else-if="item.prop=='todo'">
                                <el-popconfirm
                                        title="该操作将删除该物资数据,确定要删除吗"
                                        @confirm="delOrder(scope.$index)"
                                >
                                    <el-button  slot="reference" type="text">删除</el-button>
                                </el-popconfirm>
                            </template>
                            <template  v-else>
                                {{scope.row[item.prop]}}
                            </template>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
            <span slot="footer" class="dialog-footer">
                 <el-button size="medium" @click="resetForm">取 消</el-button>
                 <el-button size="medium" type="success" v-if="info.approveStatus == -1 || info.approveStatus == null" @click="save('ruleForm')">保 存</el-button>
                
                <el-button size="medium" type="primary" @click="submit('ruleForm')">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import ZTitle from "../../../../components/ZTitle/ZTitle";
    import MaterialInfo from "../../../common/MaterialInfo/MaterialInfo";
    import MaterialList from "../../../common/MaterialList/MaterialList";

    export default {
        components: {
            MaterialList,
            MaterialInfo,
            ZTitle
        },
        name: "inventory-manage",
        props: {
            visible: Boolean,
            id: String,
        },
        data() {
            return {
                approveStatus:'',
                value1: '',
                list: [],
                storeList:[],
                majorList: [],
                majorId: '',
                info: {
                    storeId:'',
                    remarks:'',
                    checkTime:'',
                    majorId:''
                },
                rules: {
                    storeId: [{required: true, message: '', trigger: ['blur', 'change']}],
                    checkTime: [{required: true, message: '', trigger: ['blur', 'change']}],
                },
                editMaterial: false,//编辑物资
                height:250,
                tableData:[],
                loading:false,
                rowName:[{
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
                    label: '规格型号',
                    prop: 'specsModelName',
                    width: '',
                    fixed: ''
                }, {
                    label: '计量单位',
                    prop: 'doseUnit',
                    width: '',
                    fixed: ''
                }, {
                    label: '数量',
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
                }, {
                    label: '仓库',
                    prop: 'storeName',
                    width: '',
                    fixed: ''
                }, {
                    label: '库位',
                    prop: 'gridName',
                    width: '',
                    fixed: ''
                }, {
                        label: '操作',
                        prop: 'todo',
                        width: '',
                        fixed: ''
                    }]
            }
        },
        watch: {
            visible(newval, oldval) {
                this.getMajorList();
                if (newval) {
                    this.getStoreList();

                    if(this.id){
                        this.getData();
                    }
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
            //新增物资
            addMaterial() {
                this.editMaterial = true;
            },
            //获取物资
            getMaterial(data){
                console.log(data)
                this.tableData=data;
                var ids = '';
                for(var i in data){
                    ids +=data[i].id+',';
                }
                if(ids.length>0){
                    ids=ids.substring(0,ids.length-1);
                    this.getDetailInfo(ids);
                }
            },
            //获取详细信息
            getDetailInfo(ids){
                this.$axios.post('/good/findByIds',ids).then(res=>{
                    console.log(res.data)
                    this.tableData=res.data.list;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            delOrder(idx){
                this.tableData.splice(idx,1)
            },
            getStoreList(){
                this.$axios.post('/store/storeList',{
                    "projectId": this.$root.getStorage('projectId'),
                }).then(res=>{
                    this.storeList=res.data.list;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getData(){
                this.$axios.get('/check/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.tableData=res.data.checkDetail;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            submit(formName) {
                this.approveStatus = "0";
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.id == '') {
                            this.addSubmit();
                        } else {
                            this.editSubmit();
                        }
                    } else {
                        this.$message.error('表单填写有误');
                        return false;
                    }
                });
            },
            save(formName) {
                this.approveStatus = "-1";
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.id == '') {
                            this.addSubmit();
                        } else {
                            this.editSubmit();
                        }
                    } else {
                        this.$message.error('表单填写有误');
                        return false;
                    }
                });
            },
            
            addSubmit(){
                var info=this.info;
                var checkDetail=[];
                for(var i in this.tableData){
                    if(!this.tableData[i].num){
                        return this.$message.warning('盘点数量不能为空')
                    }
                    checkDetail.push({
                        "goodId": this.tableData[i].id,
                        "gridId": this.tableData[i].gridId,
                        "num": this.tableData[i].num,
                        "stockNum": this.tableData[i].stockNum
                    })
                }
                this.$axios.post('/check/save',{
                    "projectId": this.$root.getStorage('projectId'),
                    "approveStatus": this.approveStatus,
                    "storeId": info.storeId,
                    "remarks": info.remarks,
                    "checkTime": info.checkTime,
                    "majorId": info.majorId,
                    "checkDetail": checkDetail
                }).then(res=>{
                    this.$message.success('新增盘点单成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                var checkDetail=[];
                for(var i in this.tableData){
                    if(!this.tableData[i].num){
                        return this.$message.warning('盘点数量不能为空')
                    }
                    checkDetail.push({
                        "goodId": this.tableData[i].goodId,
                        "gridId": this.tableData[i].gridId,
                        "num": this.tableData[i].num,
                        "stockNum": this.tableData[i].stockNum
                    })
                }
                this.$axios.post('/check/update',{
                    "projectId": this.$root.getStorage('projectId'),
                    "approveStatus": this.approveStatus,
                    "id":this.id,
                    "storeId": info.storeId,
                    "majorId": info.majorId,
                    "remarks": info.remarks,
                    "checkTime": info.checkTime,
                    "checkDetail": checkDetail
                }).then(res=>{
                    this.$message.success('编辑盘点单成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.tableData=[];
                this.$emit('refreshList');
                this.$refs.ruleForm.resetFields();
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped lang="scss">
    .order-manage {
        min-width: 1200px;
    }

    .order_form_text {
        background: #f8f8f8;
        width: 200px;
        font-size: 14px;
        padding: 0 10px;
    }
</style>