<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="600px"
    >
        <div slot="title" class="comm-toast-title-one">库位详情编辑</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="100px"  size="mini" :show-message="false">
                <el-form-item label="仓库名称" prop="storeName">
                    <el-input disabled v-model="info.storeName"></el-input>
                </el-form-item>
                <el-form-item label="库位编号" prop="girdNo">
                    <el-input disabled v-model="info.girdNo"></el-input>
                </el-form-item>
                    <el-form-item label="库位名称" prop="gridName">
                        <el-input maxlength="32" v-model="info.gridName" placeholder="请输入库位名称"></el-input>
                    </el-form-item>
                <el-form-item label="启用状态" prop="status">
                    <el-radio-group v-model="info.status">
                        <el-radio label="1">启用</el-radio>
                        <el-radio label="0">停用</el-radio>
                    </el-radio-group>
                </el-form-item>
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
            </el-form>

        </el-card>
        <div slot="footer" style="margin-top: -20px">
            <el-button size="medium" @click="resetForm">取 消</el-button>
            <el-button size="medium" type="primary" class="ml-10" @click="submit('ruleForm')">保 存</el-button>

        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "location-manage",
        props: {
            visible: Boolean,
            id: String,
            type:{
                type:String|Number,
                default:0
            }
        },
        data() {
            return {
                warehouse:'老王家大院',
                list: [],
                info: {
                    storeName: '',
                    girdNo:'',
                    gridName:'',
                    status:'1',
                    remarks: '',
                },
                rules: {
                    gridName: [{required: true, message: '', trigger: 'blur'}],
                    foodTypeCode: [{required: true, message: '', trigger: 'blur'}]
                },
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    this.getData();
                }
            }
        },
        methods: {
            getData(){
              this.$axios.get('/storeGrid/findById/'+this.id).then(res=>{
                  console.log(res.data)
                  this.info=res.data;
              }).catch(err=>{
                  this.$message.error(err.msg)
              })
            },
            submit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var info=this.info;
                       this.$axios.post('/storeGrid/update',{
                           "id":this.id,
                           "gridName":info.gridName,
                           "remarks": info.remarks,
                           "status": info.status
                       }).then(res=>{
                           this.$message.success('编辑网格成功');
                           this.resetForm();
                       }).catch(err=>{
                           this.$message.error(err.msg)
                       })
                    } else {
                        this.$message.error('表单填写有误');
                        return false;
                    }
                });
            },
            resetForm() {
                this.$emit('refreshList');
                this.$refs.ruleForm.resetFields();
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped lang="scss">
.tips{
    color: #999;
    font-size: 12px;
    margin-left: 30px;
    span{
        color: #eb1c1c;
    }
}
</style>