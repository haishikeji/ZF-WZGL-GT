<template>
    <div>
        <z-title title="库位网格" icon="el-icon-data-analysis"></z-title>
       <content-card>
           <!--通用搜索-->
           <el-card>
               <div class="flex-n fac">
                   <div class="comm-label">仓库</div>
                   <el-select class="ele-select" clearable  size="mini" v-model="storeId" placeholder="请选择">
                       <el-option
                               v-for="item in storeList"
                               :key="item.id"
                               :label="item.storeName"
                               :value="item.id">
                       </el-option>
                   </el-select>
                   <el-button size="mini" type="primary" class="ml-10">查询</el-button>
               </div>
           </el-card>
           <el-card class="box-card mt-10">
               <div slot="header" class="flex-n fac fjb">
                   <div class="comm-card-title-one">库位网格列表</div>
               </div>
               <div class="location-grid" v-if="list.length>0">
                   <div class="location-grid-content" :style="{'grid-template-columns': '30px repeat('+columnNumber+',calc((100% - 30px) / '+columnNumber+'))','grid-template-rows': '30px repeat('+rowNumber+', 150px)'
}">
                       <div style="grid-column-start:1;grid-column-end:2"></div>
                       <div class="location-grid-letter"  :style="{'grid-column-start': index+2,
  'grid-column-end':index+3}" v-for="(item,index) in letterList" :key="index">{{item}}
                       </div>
                       <div class="location-grid-num" :style="{'grid-row-start':i+1,'grid-row-end':i+2}"
                            v-for="i in rowNumber">{{i}}
                       </div>

                       <div class="location-grid-item" v-pointer :style="{background:item.status==0?'#f1f1f1':'white'}" v-for="(item,index) in list" :key="item.id" @click="lookLocationDetail(item.id)" >
                           <div class="location-grid-item-pos">{{item.gridNo}}</div>
                           <div class="location-grid-item-num">物资种类数量:{{item.gridName}}</div>
                       </div>
                   </div>

               </div>
           </el-card>
       </content-card>
        <!--查看库位详情-->
        <location-detail :visible.sync="locationDetailVisible" :id="id"></location-detail>
    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import LocationDetail from "./component/LocationDetail";

    export default {
        components: {
            LocationDetail,
            ContentCard,
            ZTitle
        },
        name: "location",
        data() {
            return {
                letterList: [],
                columnNumber:0,
                rowNumber:0,

                list: [],
                storeList:[],
                storeId: '',
                locationDetailVisible:false,
                id:''
            }
        },
        watch: {
            storeId(newval, oldval) {
                if (newval) {
                    this.getPositionInfo();
                }
            }
        },
        mounted(){
            this.getStoreList();
        },
        methods:{
            getStoreList(){
                this.$axios.post('/store/storeList',{
                    projectId:this.$root.getStorage('projectId')
                }).then(res=>{
                    this.storeList=res.data.list;
                     if(this.storeList.length>0){
                        if(!this.storeId){
                        this.storeId=res.data.list[0].id||''
                    }
                    }
                   
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            lookLocationDetail(id){
                this.id=id;
                this.locationDetailVisible=true;
            },
            getPositionInfo() {
                this.$axios.post('/storeGrid/findByStoreId', {
                    "storeId": this.storeId,
                }).then(res => {
                    var list = [];
                    console.log(res.data, res.data.list)
                    for (var i in res.data.list) {
                        list.push(res.data.list[i]);
                    }
                    this.list = list;
                    this.getGridCount();
                }).catch(err => {
                    this.$message.error(err.msg);
                })

            },
            //画网格
            getGridCount(list) {
                this.$axios.get('/storeGrid/gridCount/' + this.storeId).then(res => {
                    console.log('网格行列', res.data)
                    var num = Number(res.data.columnCount);
                    this.columnNumber=num;
                    this.rowNumber=res.data.rowCount;
                    var letterList = [];
                    for (var i = 0; i < num; i++) {
                        letterList.push(String.fromCharCode(65 + i))
                    }
                    this.letterList = letterList;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
        }

    }
</script>

<style scoped lang="scss">
    .location-grid {
        overflow-x: scroll;
        background: #d6d6d6;
        border: 1px solid #d6d6d6;
        .location-grid-content {
            background: #d6d6d6;
            display: grid;
            grid-gap: 1px;
            width: auto;
            grid-template-columns:30px repeat(7, 18%);//需要计算
            grid-template-rows: 30px repeat(5, 150px);
            .location-grid-letter {

                line-height: 30px;
                grid-row-start: 1;
                grid-row-end: 2;
            }
            .location-grid-num {
                grid-column-start: 1;
                grid-column-end: 2;
                line-height: 150px;
            }
            .location-grid-letter,.location-grid-num{
                text-align: center;
                background: #f5f7fa;
                font-weight: bold;
                color: #777;
            }
            .location-grid-item{
                text-align: center;
                .location-grid-item-pos{
                    margin-top: 30px;
                    font-size: 40px;
                }
                .location-grid-item-num{
                    margin-top: 10px;

                }
            }
            div {
                background: white;
            }
        }
    }

</style>