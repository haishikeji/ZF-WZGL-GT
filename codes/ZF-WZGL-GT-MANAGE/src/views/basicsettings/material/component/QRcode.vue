<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="320px"
    >
        <div slot="title" class="comm-toast-title-one">物资二维码</div>
        <el-card id="content" shadow="never">
            <!--style="display: flex;flex-wrap: nowrap;align-items: center"-->
           <div  style="position: relative;">
               <div style="font-weight: bold;text-align: center;z-index: 100;position: absolute;width: 100%">{{info.goodName}}</div>
               <!--<div>-->
                   <!--<div style="margin-top: 5px;margin-bottom: 5px;font-weight: bold">{{info.goodName}}</div>-->
                   <!--<div style="margin-top: 5px;margin-bottom: 5px;">物资编号<span style="margin-left: 30px;color: #999;">{{info.goodCode}}</span></div>-->
                   <!--<div style="margin-top: 5px;margin-bottom: 5px;">规格型号<span style="margin-left: 30px;color: #999;">{{info.specsModelName}}</span></div>-->
                   <!--<div style="margin-top: 5px;margin-bottom: 5px;">物资类别<span style="margin-left: 30px;color: #999;">{{info.typeName}}</span></div>-->
                   <!--<div style="margin-top: 5px;margin-bottom: 5px;">计量单位<span style="margin-left: 30px;color: #999;">{{info.measureUnit}}</span></div>-->
               <!--</div>-->
               <img  style="width: 200px;height: 200px;margin-left: 20px;" :src="$root.getStorage('url')" id="qrcode">

           </div>
             
        </el-card>
        <!--<div id="content">-->
            <!--<div>111</div>-->
            <!---->
        <!--</div>-->
        <div slot="footer" style="margin-top: -20px">
            <el-button size="medium" @click="resetForm">取 消</el-button>
            <el-button size="medium" type="primary" class="ml-10" :loading="loading" @click="printing">打印</el-button>

        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "q-rcode",
        props: {
            visible: Boolean,
            id: String
        },
        data() {
            return {
                info:{},
                loading:false,
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {

                    this.loading=true;
                    this.getData();
                    this.getUrl();
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/good/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getUrl() {
                this.$axios.post('/qrcode/goodsQRCode', {
                    goodId: this.id
                }).then(res => {
                    console.log(res)
                    this.$nextTick(()=>{
                        this.$root.setStorage('url',res.data.url);
                    })
                    this.loading=false;
                }).catch(err => {
                    this.$message.error(err.msg)
                    this.loading=false;
                })
            },
            printing() {
                var bdhtml = document.getElementById('content').innerHTML;

                var newWindow = window.open("打印窗口", "_blank");
                newWindow.document.write(bdhtml);
                newWindow.document.close();
                newWindow.print();
                newWindow.close();
            },
            resetForm() {
                this.$emit('refreshList');
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped lang="scss">

</style>