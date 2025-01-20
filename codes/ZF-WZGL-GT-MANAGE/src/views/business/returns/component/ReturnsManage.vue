<template>
    <div>
        <el-dialog
                class="order-manage"
                :visible.sync="visible"
                :show-close="false"
                fullscreen
                center
        >
            <z-title slot="title" :title="(id==''?'新增':'编辑')+'退货单'" icon="el-icon-receiving" class="common-fixed-title">
                <el-button size="medium" @click="resetForm">返回</el-button>
            </z-title>


                <el-form :model="info" :rules="rules" ref="ruleForm" label-width="120px"  size="mini" :show-message="false">
                    <el-card shadow="never" class="mt-10" v-if="id!=''">
                    <div class="flex-n" >
                        <el-form-item label="退货单号" prop="returnNo">
                            <div class="order_form_text">{{info.returnNo}}</div>
                        </el-form-item>
                        <el-form-item label="制单人" prop="createUserName">
                            <div class="order_form_text">{{info.createUserName}}</div>
                        </el-form-item>
                        <el-form-item label="制单时间" prop="createTime">
                            <div class="order_form_text">{{info.createTime}}</div>
                        </el-form-item>
                    </div>
                    </el-card>
                </el-form>

            <!--物资信息列表-->
            <material-info :type="3" style="margin-bottom: -20px" v-model="tableData">
                <el-button size="mini" type="primary" @click="addMaterial">新增物资</el-button>
                <material-list :visible.sync="editMaterial" @getMaterial="getMaterial" :data="tableData"></material-list>
            </material-info>
            <span slot="footer" class="dialog-footer">
                 <el-button size="medium" @click="resetForm">取 消</el-button>
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
        name: "returns-manage",
        props: {
            visible: Boolean,
            id: String,
        },
        data() {
            return {
                tableData:[],
                info: {},
                rules: {},
                editMaterial: false,//编辑定测量物资
                hisList: [],
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    if(this.id){
                        this.getData();
                    }
                }
            }
        },
        methods: {
            //新增物资
            addMaterial() {
                this.editMaterial = true;
            },
            //获取物资
            getMaterial(data){
                console.log(data)
                this.tableData=data;
                var ids='';
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
                    var data = res.data.list;
                    for (var i in data) {
                        var num = -1;
                        for (var j in this.tableData) {
                            if (this.tableData[j].goodId == data[i].goodId) {
                                num = j;
                                break;
                            }
                        }
                        if (num == -1) {
                            data[i].num = 1;
                        }
                    }
                    this.tableData = data;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getData(){
                this.$axios.get('/returnGood/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.tableData=res.data.goodDetail;
                    this.hisList = JSON.parse(JSON.stringify(this.tableData));
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            submit(formName) {
                console.log(formName)
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(1)
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
                var goodDetail=[];
                for(var i in this.tableData){
                    if(!this.tableData[i].num){
                        return this.$message.warning('数量不能为空')
                    }
                    goodDetail.push({
                        "goodId": this.tableData[i].goodId,
                        "gridId": this.tableData[i].gridId,
                        "num": this.tableData[i].num
                    })
                }
                this.$axios.post('/returnGood/save',{
                    "projectId":this.$root.getStorage('projectId'),
                    "goodDetail": goodDetail
                }).then(res=>{
                    this.$message.success('新增退货单成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                var goodDetail=[];
                for(var i in this.tableData){
                    if(!this.tableData[i].num){
                        return this.$message.warning('数量不能为空')
                    }
                    goodDetail.push({
                        "goodId": this.tableData[i].goodId,
                        "gridId": this.tableData[i].gridId,
                        "num": this.tableData[i].num
                    })
                }
                for (var i in this.hisList) {
                    for (var j in goodDetail) {
                        if (this.hisList[i].goodId == goodDetail[j].goodId) {
                            goodDetail[j].id = this.hisList[i].id;
                        }
                    }
                }
                this.$axios.post('/returnGood/update',{
                    "id":this.id,
                    "goodDetail": goodDetail
                }).then(res=>{
                    this.$message.success('编辑退货单成功')
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
        },
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