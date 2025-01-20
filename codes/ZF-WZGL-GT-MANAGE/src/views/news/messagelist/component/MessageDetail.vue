<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="900px"
    >
        <div slot="title" class="comm-toast-title-one">{{title}}</div>

        <el-card shadow="never">
            <div class="info-area" v-if="type==1">
                <div class="top" v-if="title=='图纸量超过概算量'">
                    图纸量 <i class="el-icon-arrow-right"></i> 概算量
                </div>
                <div class="top" v-if="title=='定测量超过图纸量'">
                    定测量<i class="el-icon-arrow-right"></i>图纸量
                </div>
                <div class="text">物资名称</div>
                <div class="num">22323</div>
                <div class="text">物资名称</div>
                <div class="num">丽丽</div>
                <div class="text">合计</div>
                <div class="num">五二一也</div>
                <div class="text">合计</div>
                <div class="num">呼呼呼</div>
            </div>
            <div class="info-area-2" v-else>
                <div class="text">订单编号</div>
                <div class="num">22323</div>
                <div class="text">审核状态</div>
                <div class="num">丽丽</div>
                <div class="text">制单人</div>
                <div class="num">五二一也</div>
                <div class="text">创建时间</div>
                <div class="num">呼呼呼</div>
            </div>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "message-detail",
        props: {
            visible: Boolean,
            title:String,
            id:String
        },
        data() {
            return {
                type:0
            }
        },
        watch:{
          visible(newval,oldval){
              if(newval){
                  if(this.title=='图纸量超过概算量'||this.title=='定测量超过图纸量'){
                      this.type=1;
                  }else{
                      this.type=0;
                  }
                  this.getData();
              }
          }
        },
        methods: {
            getData(){
                this.isRead();
            },
            isRead(){
                this.$axios.post('/msg/updateIsRead',{
                    id:this.id
                }).then(res=>{

                }).catch(err=>{

                })
            },
            resetForm() {
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped lang="scss">
    .info-area {
        display: grid;
        width: 100%;
        grid-row-gap: 30px;
        grid-template-columns: repeat(2, 10% 40%);
        font-size: 14px;
        .top{
            grid-column-start: 1;
            grid-column-end:5;
            text-align: center;
            font-size: 30px;
            background: #f5f7fa;
        }
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
    .info-area-2{
        display: grid;
        width: 100%;
        grid-row-gap: 30px;
        grid-template-columns: 20% 80%;
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
            margin-right: 20%;
        }
    }
</style>