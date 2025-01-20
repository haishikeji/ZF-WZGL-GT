<template>
    <el-dialog
            title="提示"
            :visible.sync="visible"
            width="500px"
            :show-close="false">
        <span>{{title}}</span>
        <span slot="footer" class="dialog-footer">
    <el-button size="medium" @click="$emit('update:visible',false)">取 消</el-button>
    <el-button size="medium" type="primary" @click="submit">确 定</el-button>
  </span>
    </el-dialog>
</template>

<script>
    export default {
        name: "confirm-toast",
        props:{
            visible:Boolean,
            title:String
        },
        methods:{
            submit(){
                this.$emit('submit');
                this.$emit('update:visible',false);
            }
        }
    }
</script>

<style scoped>

</style>