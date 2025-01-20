<template>
    <el-dialog class="order-manage" :visible.sync="visible" :before-close="resetForm" width="600px">
        <div slot="title" class="comm-toast-title-one">{{ id == '' ? '新增' : '编辑' }}领料单位</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="120px" size="mini" :show-message="false">

                <el-form-item label="领料单位编码" prop="pickCompanyNo">
                    <el-input maxlength="32" v-model.number="info.pickCompanyNo" placeholder="请输入领料单位编码"></el-input>
                </el-form-item>
                <el-form-item label="领料单位名称" prop="pickCompanyName">
                    <el-input maxlength="32" v-model="info.pickCompanyName" placeholder="请输入领料单位名称"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="remarks">
                    <el-input type="textarea" maxlength="256" :rows="5" show-word-limit placeholder="请输入备注"
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
    name: "pick-unit-manage",
    props: {
        visible: Boolean,
        id: String
    },
    data() {
        return {
            info: {
                pickCompanyNo: '',
                pickCompanyName: '',
                remarks: ''
            },
            rules: {
                pickCompanyNo: [{ required: true, message: '', trigger: 'blur' }],
                pickCompanyName: [{ required: true, message: '', trigger: 'blur' }]
            },
        }
    },
    watch: {
        visible(newval, oldval) {
            if (newval) {
                if (this.id) {
                    this.getData();
                }
            }
        }
    },
    methods: {
        getData() {
            this.$axios.get('/pickCompany/findById/' + this.id).then(res => {
                console.log(res.data);
                this.info = res.data;

            }).catch(err => {
                this.$message.error(err.msg)
            })
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
        addSubmit() {
            var info = this.info;
            this.$axios.post('/pickCompany/save', {
                "projectId": this.$root.getStorage('projectId'),
                "remarks": info.remarks,
                "pickCompanyNo": info.pickCompanyNo,
                "pickCompanyName": info.pickCompanyName
            }).then(res => {
                this.$message.success('新增领料单位成功')
                this.resetForm();
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
        editSubmit() {
            var info = this.info;
            this.$axios.post('/pickCompany/update', {
                "id": this.id,
                "remarks": info.remarks,
                "pickCompanyNo": info.pickCompanyNo,
                "pickCompanyName": info.pickCompanyName
            }).then(res => {
                this.$message.success('编辑领料单位成功')
                this.resetForm();
            }).catch(err => {
                this.$message.error(err.msg)
            })
        },
        resetForm() {
            this.$emit('refreshList');
            this.$refs.ruleForm.resetFields();
            this.$emit('update:visible', false);
        }
    }
}
</script>

<style scoped></style>