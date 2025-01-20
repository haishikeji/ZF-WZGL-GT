<template>
    <div>
        <el-dialog
                class="order-manage"
                :visible.sync="visible"
                :show-close="false"
                fullscreen
        >
            <z-title slot="title" :title="(id==''?'新增':'编辑')+'发料单'" icon="el-icon-receiving" class="common-fixed-title">
                <el-button size="medium" @click="resetForm">返回</el-button>
            </z-title>
            <el-card shadow="never" class="mt-10">
                <el-form :model="info" class="order_form" :rules="rules" size="mini" ref="ruleForm" label-width="120px" :show-message="false">
                    <div class="flex-n">

                        <el-form-item label="编号" prop="handNo">
                            <el-input v-model="info.handNo" :disabled="id != ''" class="w-140" clearable size="mini" placeholder="请输入发料编号"></el-input>
                        </el-form-item>
                        
                        <el-form-item label="专业" label-width="80px" prop="majorId">
                            <el-select  class="ele-select" clearable v-model="info.majorId" placeholder="请选择">
                                <el-option v-for="item in majorList" :key="item.id" :label="item.majorName"
                                    :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="使用站区间" prop="stationIntervalId">
                            <!-- <el-select class="ele-select" clearable   v-model="info.stationIntervalId" placeholder="请选择">
                                <el-option
                                        v-for="item in stationIntervalList"
                                        :key="item.id"
                                        :label="item.stationIntervalName"
                                        :value="item.id">
                                </el-option>
                            </el-select> -->
                            <el-autocomplete :disabled ="id != ''" class="inline-input" v-model="state1" style="padding-left: 8px; width: 280px;"
                            :fetch-suggestions="querySearch" placeholder="请输入内容"
                            @select="handleSelect"></el-autocomplete>

                        </el-form-item>
                        <el-form-item label="领料单位"    prop="pickCompanyId">
                            <el-select class="ele-select" clearable   v-model="info.pickCompanyId" placeholder="请选择">
                                <el-option
                                        v-for="item in pickCompanyList"
                                        :key="item.id"
                                        :label="item.pickCompanyName"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="领料人" prop="pickUser">
                            <el-input v-model="info.pickUser" class="w-140" clearable size="mini" placeholder="请输入领料人"></el-input>

                        </el-form-item>
                    </div>
                </el-form>
            </el-card>
            <!--物资信息列表-->
            <material-info v-model="tableData" :type="3">
                <el-button size="mini" type="primary" @click="choosePickOrder">选择领料单</el-button>
                <pick-order-list :visible.sync="choosePickOrderVisible" @getMaterial="getMaterial"></pick-order-list>
            </material-info>

                <!--需要改-->
                <div class="flex-n fje mt-20">
                 <!--<el-button size="medium" type="primary" @click="resetForm">上传附件</el-button>-->
                <div class="flex-n fac">
                 <el-button size="medium" @click="resetForm">取 消</el-button>
                 <el-button size="medium" type="success" v-if="info.approveStatus == -1 || info.approveStatus == null" @click="save('ruleForm')">保 存</el-button>
                <el-button size="medium" type="primary" @click="submit('ruleForm')">提 交</el-button>
                    </div>
                    </div>
        </el-dialog>
    </div>
</template>

<script>
    import ZTitle from "../../../../components/ZTitle/ZTitle";
    import MaterialInfo from "../../../common/MaterialInfo/MaterialInfo";
    import PickOrderList from "./PickOrderList";

    export default {
        components: {
            PickOrderList,
            MaterialInfo,
            ZTitle
        },
        name: "issue-manage",
        props: {
            visible: Boolean,
            id: String,
        },
        data() {
            return {
            stationIntervalIdNames: [],
            inputVisible: false,
            inputValue: '',
            restaurants: [],
            majorList: [],
            state1: '',
                list: [],
                info: {
                    pickCompanyId:'',
                    pickUser:'',
                    stationIntervalId:'',
                    handNo:'',
                },
                rules: {
                    pickCompanyId: [{required: true, message: '', trigger: ['blur', 'change']}],
                    handNo:[{required: true, message: '', trigger: ['blur', 'change']}],
                    stationIntervalId: [{required: true, message: '', trigger: ['blur', 'change']}],
                majorId: [{ required: true, message: '', trigger: ['blur', 'change'] }],

                },
                choosePickOrderVisible: false,//领料单选择

                stationIntervalList:[],
                pickCompanyList:[],
                approveStatus: '',

                tableData:[]
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    if(this.id){
                        this.getData();
                    }
                    this.getStationIntervalList();
                    this.getPickCompanyList();
                    this.getMajorList();
                }
            }
        },
        methods: {
            querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return (restaurant) => {
                return (restaurant.value.includes(queryString));
            };
        },
        
        handleSelect(item) {
            let inputValue = this.state1;
            if (inputValue) {
                this.info.stationIntervalId = item.id;
            }
        },

            //站区间列表
            getStationIntervalList(){
                this.$axios.post('/stationInterval/findList',{
                    "belongProjectId": this.$root.getStorage('projectId'),
                }).then(res=>{
                    this.stationIntervalList=res.data.list;
                    this.restaurants = [];
                this.stationIntervalList.forEach((item, key) => {
                    const dataOne = { "value": item.stationIntervalName, "id": item.id };
                    this.restaurants.push(dataOne);
                });
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
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
            //领料单选择
            choosePickOrder() {
                this.choosePickOrderVisible = true;
            },
            //获取物资
            getMaterial(data){
                console.log(data)
                this.tableData=data;
                var pickOrderIds = [];
                for(var i in data){
                    pickOrderIds.push(data[i].id);
                }
                this.getDetailInfo(pickOrderIds);
            },
            //获取详细信息
            getDetailInfo(pickOrderIds){
                this.$axios.post('/pickOrderDetail/pickOrderDetailList',{
                    pickOrderIds:pickOrderIds
                }).then(res=>{
                    console.log(res.data)
                    const data=res.data.list;
                    for(var i in data){
                        console.log(data[i])
                        data[i].num = data[i].balance 
                    }
                    this.tableData=data;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getData(){
                this.$axios.get('/sendOrder/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.tableData=res.data.sendDetail;
                    this.stationIntervalList.forEach((item, key) => {
                    if(item.id===this.info.stationIntervalId){
                        this.state1=item.stationIntervalName
                    }
                    // const dataOne = { "value": item.stationIntervalName, "id": item.id };
                    // this.restaurants.push(dataOne);
                });
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
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
                var sendDetail=[];
                for(var i in this.tableData){
                    if(!this.tableData[i].num){
                        return this.$message.warning('数量不能为空')
                    }
                    sendDetail.push({
                        "pickOrderId": this.tableData[i].pickOrderId,
                        "pickOrderDetailId": this.tableData[i].id,
                        "gridId": this.tableData[i].gridId,
                        "num": this.tableData[i].num,
                        "orgNum": this.tableData[i].orgNum,
                    })
                }
                this.$axios.post('/sendOrder/save',{
                    "projectId": this.$root.getStorage('projectId'),
                    "approveStatus": this.approveStatus,
                    "pickUser": info.pickUser,
                    "pickCompanyId": info.pickCompanyId,
                    "stationIntervalId": info.stationIntervalId,
                    "handNo": info.handNo,
                    "majorId": info.majorId,
                    "sendDetail": sendDetail
                }).then(res=>{
                    this.$message.success('新增发料单成功')
                    this.resetForm();
                }).catch(err=>{
                    // this.$message.error(err.msg)
                    this.$alert(err.msg, '', {
                        cancelButtonText: '取消',
                        type: 'error'
                        })
                })
            },
            editSubmit(){
                var info=this.info;
                var sendDetail=[];
                for(var i in this.tableData){
                    if(!this.tableData[i].num){
                        return this.$message.warning('数量不能为空')
                    }
                    console.log(this.tableData[i].pickOrderDetailId )
                    sendDetail.push({
                        "pickOrderId": this.tableData[i].pickOrderId,
                        "pickOrderDetailId": this.tableData[i].pickOrderDetailId ,
                        "gridId": this.tableData[i].gridId,
                        "num": this.tableData[i].num,
                        "orgNum": this.tableData[i].orgNum,
                    })
                }
                this.$axios.post('/sendOrder/update',{
                    "projectId": this.$root.getStorage('projectId'),
                    "approveStatus": this.approveStatus,
                    "id":this.id,
                    "pickUser": info.pickUser,
                    "pickCompanyId": info.pickCompanyId,
                    "stationIntervalId": info.stationIntervalId,
                    "handNo": info.handNo,
                    "majorId": info.majorId,
                    "sendDetail": sendDetail
                }).then(res=>{
                    this.$message.success('编辑发料单成功')
                    this.resetForm();
                }).catch(err=>{
                    // this.$message.error(err.msg)
                    this.$alert(err.msg, '', {
                        cancelButtonText: '取消',
                        type: 'error'
                        })
                })
            },
            resetForm() {
                this.tableData=[];
                this.$emit('refreshList');
                this.$refs.ruleForm.resetFields();
                this.$emit('update:visible', false);
                this.state1 =''
                this.info={
                    pickCompanyId:'',
                    pickUser:'',
                    stationIntervalId:'',
                    handNo:'',
                }
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