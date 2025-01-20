<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1200px"
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}图纸量</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="120px"  size="mini" :show-message="false">
                <div class="flex-n fac">
                    <el-form-item label="物资编号" prop="goodCode">
                        <el-input maxlength="32" class="w-220" disabled v-model="info.goodCode" placeholder="请输入物资编号"></el-input>
                    </el-form-item>
                    <el-form-item label="物资名称" prop="goodName">
                        <el-input maxlength="32" class="w-220" disabled v-model="info.goodName" placeholder="请输入物资名称"></el-input>
                    </el-form-item>
                    <el-form-item label="规格型号" prop="specsModelName">
                        <el-input maxlength="32" class="w-220" disabled v-model="info.specsModelName" placeholder="请输入规格型号"></el-input>

                        <!--<el-select class="ele-select w-220"  clearable :value="info.a" placeholder="请选择">-->
                            <!--<el-option-->
                                    <!--v-for="item in list"-->
                                    <!--:key="item"-->
                                    <!--:label="item"-->
                                    <!--:value="item">-->
                            <!--</el-option>-->
                        <!--</el-select>-->
                        <el-button size="mini" class="ml-10" type="primary" @click="selectMaterial">选择物资</el-button>

                    </el-form-item>
                </div>
                <div class="flex-n">
                    <el-form-item label="单位" prop="unit">
                        <el-input maxlength="32" class="w-220" disabled v-model="info.unit" placeholder="请输入单位"></el-input>
                    </el-form-item>
                    <template v-if="id">
                    <el-form-item label="制单人" prop="createUserName">
                        <div class="w-220">{{info.createUserName}}</div>
                    </el-form-item>
                    <el-form-item label="制单时间" prop="createTime">
                        <div>{{info.createTime}}</div>
                    </el-form-item>
                    </template>

                </div>
                <el-form-item label="描述" prop="remarks">
                    <el-input
                            type="textarea"
                            maxlength="256"
                            :rows="5"

                            show-word-limit
                            placeholder="请输入描述"
                            v-model="info.remarks">
                    </el-input>
                </el-form-item>
                <el-row :gutter="20">
                    <el-col :span="8" v-for="(item,index) in stationScheduleList" :key="index">
                        <el-form-item :label="item.stationIntervalName" prop="">
                            <el-input maxlength="32" class="w-220" v-model.number="item.stationScheduleValue" placeholder="请输入"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>

        </el-card>
        <div slot="footer" style="margin-top: -20px">
            <el-button size="medium" @click="resetForm">取 消</el-button>
            <el-button size="medium" type="primary" class="ml-10" @click="submit('ruleForm')">保 存</el-button>

        </div>
        <material-list :visible.sync="editMaterialVisible" :type="2" @getMaterial="getMaterial" :data="info"></material-list>
    </el-dialog>
</template>

<script>
    import MaterialList from "../../../common/MaterialList/MaterialList";

    export default {
        components: {MaterialList},
        name: "drawing-manage",
        props: {
            visible: Boolean,
            id: String
        },
        data() {
            return {
                list: [],
                info: {
                },
                rules: {

                },
                editMaterialVisible:false,
                stationScheduleList:[]
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    this.getStationSchedule();
                }
            }
        },
        methods: {
            getData(data){
                this.$axios.get('/threeEstimate/findBudgetDrawingById/'+this.id).then(res=>{
                    this.info=res.data;
                    var column=this.info.column;
                    var stationScheduleList=[]
                    for(var i in column){
                        for(var j in data){
                            if(column[i].id==data[j].id){
                                stationScheduleList.push({
                                    stationScheduleValue:column[i].stationScheduleValue,
                                    id:column[i].stationScheduleId,
                                    stationScheduleId:column[i].id,
                                    stationIntervalName:column[i].stationIntervalName
                                })
                            }
                        }
                    }
                    this.stationScheduleList=stationScheduleList;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getStationSchedule(){
                this.$axios.get('/stationSchedule/findStationSchedule/'+this.$root.getStorage('projectId')).then(res => {
                    if(this.id){
                        this.getData(res.data.list);
                    }else
                    this.stationScheduleList = res.data.list;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
            selectMaterial(){
              this.editMaterialVisible=true;
            },
            getMaterial(data){
                console.log(data)
                this.info=data;
                this.$set(this.info,'unit',data.measureUnit);
            },
            submit(formName) {
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
                if(!info.goodId){
                    return this.$message.warning('请选择物资')
                }
                var stationScheduleModels=[];
                for(var i in this.stationScheduleList){
                    stationScheduleModels.push({
                        "stationScheduleId": this.stationScheduleList[i].id,
                        "stationScheduleValue":  this.stationScheduleList[i].stationScheduleValue
                    })
                }
                this.$axios.post('/threeEstimate/saveBudgetDrawing',{
                    "stationScheduleModels":stationScheduleModels,
                    "goodId": info.goodId,
                    "projectId": this.$root.getStorage('projectId'),
                    "remarks": info.remarks,
                    "specsModelName": info.specsModelName,
                    "unit":info.unit
                }).then(res=>{
                    this.$message.success('新增图纸量成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                var stationScheduleModels=[];
                for(var i in this.stationScheduleList){
                    stationScheduleModels.push({
                        "id": this.stationScheduleList[i].id,
                        "stationScheduleId": this.stationScheduleList[i].stationScheduleId,
                        "stationScheduleValue":  this.stationScheduleList[i].stationScheduleValue
                    })
                }
                this.$axios.post('/threeEstimate/updateBudgetDrawing',{
                    "id":this.id,
                    "stationScheduleModels": stationScheduleModels,
                    "goodId": info.goodId,
                    "remarks": info.remarks,
                    "specsModelName": info.specsModelName,
                    "unit":info.unit
                }).then(res=>{
                    this.$message.success('编辑图纸量成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.stationScheduleList=[];
                this.info={};
                this.$emit('refreshList');
                this.$refs.ruleForm.resetFields();
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped>

</style>