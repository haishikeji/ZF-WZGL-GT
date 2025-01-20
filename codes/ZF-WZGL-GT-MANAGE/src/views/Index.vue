<template>
    <div>
        <z-title title="主页" icon="el-icon-s-home"></z-title>
       <content-card>
           <el-card class="box-card">
               <el-row :gutter="20">
                   <el-col :span="4">
                       <el-card class="box-card total-card" shadow="hover">
                           <div class="text1">库存总量</div>
                           <div class="text2">{{statisticalInfo.stockNum||0}}</div>
                       </el-card>
                   </el-col>
                   <el-col :span="4">
                       <el-card class="box-card total-card" shadow="hover">
                           <div class="text1">本月领料单数量</div>
                           <div class="text2">{{statisticalInfo.pickNum||0}}</div>
                       </el-card>
                   </el-col>
                   <el-col :span="4">
                       <el-card class="box-card total-card" shadow="hover">
                           <div class="text1">本月发料单数量</div>
                           <div class="text2">{{statisticalInfo.sendNum||0}}</div>
                       </el-card>
                   </el-col>
                   <el-col :span="4">
                       <el-card class="box-card total-card" shadow="hover">
                           <div class="text1">本月入库数量</div>
                           <div class="text2">{{statisticalInfo.inputNum||0}}</div>
                       </el-card>
                   </el-col>
                   <el-col :span="4">
                       <el-card class="box-card total-card" shadow="hover">
                           <div class="text1">本月出库数量</div>
                           <div class="text2">{{statisticalInfo.outputNum||0}}</div>
                       </el-card>
                   </el-col>
                   <el-col :span="4">
                       <el-card class="box-card total-card" shadow="hover">
                           <div class="text1">本月调拨出库数量</div>
                           <div class="text2">{{statisticalInfo.allotNum||0}}</div>
                       </el-card>
                   </el-col>
               </el-row>
           </el-card>
           <el-row class="mt-20" :gutter="20">
               <el-col :span="16">
                   <el-card>
                       <div slot="header" class="comm-card-title-one">仓库操作统计</div>
                       <div id="echart"></div>
                   </el-card>
               </el-col>
               <el-col :span="8">
                   <el-card>
                       <div slot="header" class="comm-card-title-one">待处理业务列表</div>

                       <div class="business-area flex-n fac fjb" v-for="(item,index) in bussinessList" :key="item.id" v-pointer @click="gotoPage(item.taskNodeName)">
                           <div class="text1">{{item.taskNodeName}}单</div>
                           <div class="text2">{{item.sum||0}}</div>
                       </div>
                   </el-card>
               </el-col>
           </el-row>
       </content-card>

    </div>
</template>

<script>
    import ZTitle from "../components/ZTitle/ZTitle";
    import ContentCard from "../components/ContentCard/ContentCard";
    import * as echarts from 'echarts';
    export default {
        components: {
            ContentCard,
            ZTitle},
        name: "index",
        data() {
            return {
                statisticalInfo:{},
                bussinessInfo:{},
                bussinessList:[],
                checkCount:[],
                inputCount:[],
                outputCount:[],
                allotCount:[],
                dateSet:[],
                monthday:[31,28,31,30,31,30,31,31,30,31,30,31]
            }
        },
        mounted(){
            this.getStatisticalInfo();
            this.getTodoList();
            this.getIndexCount();
        },
        methods:{
   //上方统计数量
    getStatisticalInfo(){
        var date=new Date();
        // this.getRunnian(date.getFullYear());

        this.$axios.post('/report/calcStockNum',{
            projectId:this.$root.getStorage('projectId'),
            "beginTime": date.getFullYear()+'-'+(date.getMonth()+1)+'-1',
            "endTime":  date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(),
        }).then(res=>{
            this.statisticalInfo=res.data.list[0]||{};
        }).catch(err=>{
            this.$message.error(err.msg);
        })
    },
            getRunnian(yy){
                if(yy % 4 == 0 && yy % 100 !== 0 || yy % 400 == 0){
                    this.monthday[1]=29;
                }else{
                    this.monthday[1]=28;
                }
            },
            //仓库操作统计
            getStoreOperateStatistics(){
                this.$axios.post('/index/storeOperateStatistics',{
                    projectNo:this.$root.getStorage('projectId')
                }).then(res=>{

                }).catch(err=>{
                    this.$message.error(err.msg);
                })
            },
            //待处理业务

        getTodoList(){
            this.$axios.post('/workflow/todoList',{
            }).then(res=>{
                console.log('折线图统计',res.data)
                this.bussinessList=res.data.list;
            }).catch(err=>{
                this.$message.error(err.msg);
            })
        },

        getIndexCount(){
            this.$axios.post('/workflow/indexCount',{
                  "projectId":this.$root.getStorage('projectId')
            }).then(res=>{
                console.log('待处理业务',res.data.list)
                // this.bussinessList=res.data.list;
                this.checkCount=res.data.checkCount;
                 this.inputCount=res.data.inputCount;
                  this.outputCount=res.data.outputCount;
                   this.allotCount=res.data.allotCount;
                    this.dateSet=res.data.dateSet;
            this.getTotal();

            }).catch(err=>{
                this.$message.error(err.msg);
            })
        },
            //跳转
            gotoPage(name){
        this.$router.push({path:'/backlog/manage/wait',query:{taskNodeName:name}})
            },
            getTotal(){
                var myChart = echarts.init(document.getElementById('echart'));
                const xData=[],yData=[];
                var text='';


                var option = {
                    backgroundColor: '#fff',
                    dataZoom: [
                        {
                            show: true,
                            realtime: true,
                            start: 0,
                            end: 80
                        },
                        {
                            type: 'inside',
                            realtime: true,
                            start: 0,
                            end: 80
                        }
                    ],
                    legend: {
                        icon: 'circle',
                        top: '0%',
                        right: '5%',
                        itemWidth: 6,
                        itemGap: 20,
                        textStyle: {
                            color: '#556677'
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            label: {
                                show: true,
                                backgroundColor: '#fff',
                                color: '#556677',
                                borderColor: 'rgba(0,0,0,0)',
                                shadowColor: 'rgba(0,0,0,0)',
                                shadowOffsetY: 0
                            },
                            lineStyle: {
                                width: 0
                            }
                        },
                        backgroundColor: '#fff',
                        textStyle: {
                            color: '#5c6c7c'
                        },
                        padding: [10, 10],
                        extraCssText: 'box-shadow: 1px 0 2px 0 rgba(163,163,163,0.5)'
                    },
                    grid: {
                        top: '8%',
                        right:'2%',
                        left:'5%'
                    },
                    xAxis: [{
                        type: 'category',
                        data: this.dateSet,
                        axisLine: {
                            lineStyle: {
                                color: 'rgba(107,107,107,0.37)', //x轴颜色
                            }
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            interval: 0,
                            textStyle: {
                                color: '#999' //坐标轴字颜色
                            },
                            margin: 15
                        },
                        axisPointer: {
                            label: {
                                padding: [11, 5, 7],
                            }
                        },
                        boundaryGap: false
                    }],
                    yAxis: [{
                        type: 'value',
                        axisTick: {
                            show: false
                        },
                        axisLine: {
                            show: false,
                            // lineStyle: {
                            //     color: 'rgba(107,107,107,0.37)', //y轴颜色
                            // }
                        },
                        axisLabel: {
                            textStyle: {
                                color: '#999'
                            }
                        },
                        splitLine: {
                            show: false
                        }
                    }],
                    series: [{
                        name: '入库',
                        type: 'line',
                        data:this.inputCount,
                        symbolSize: 10,
                        symbol: 'circle',
                        yAxisIndex: 0,
                        showSymbol: true,
                        lineStyle: {
                            width: 2,
                            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                                offset: 0,
                                color: '#6d79ff'
                            },
                                {
                                    offset: 1,
                                    color: '#4B85FF'
                                }
                            ]),
                            shadowColor: 'rgba(158,135,255, 0.3)',
                            shadowBlur: 10,
                            shadowOffsetY: 20
                        },
                        itemStyle: {
                            normal: {
                                color: '#4B85FF',
                                borderColor: '#4B85FF'
                            }
                        }
                    },
                        {
                        name: '出库',
                        type: 'line',
                        data:this.outputCount,
                        symbolSize: 10,
                        symbol: 'circle',
                        yAxisIndex: 0,
                        showSymbol: true,
                        lineStyle: {
                            width: 2,
                            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                                offset: 0,
                                color: '#03d243'
                            },
                                {
                                    offset: 1,
                                    color: '#95d787'
                                }
                            ]),
                            shadowColor: 'rgba(158,135,255, 0.3)',
                            shadowBlur: 10,
                            shadowOffsetY: 20
                        },
                        itemStyle: {
                            normal: {
                                color: '#03d243',
                                borderColor: '#03d243'
                            }
                        }
                    },
                        {
                        name: '盘点',
                        type: 'line',
                        data:this.checkCount,
                        symbolSize: 10,
                        symbol: 'circle',
                        yAxisIndex: 0,
                        showSymbol: true,
                        lineStyle: {
                            width: 2,
                            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                                offset: 0,
                                color: '#fff75e'
                            },
                                {
                                    offset: 1,
                                    color: '#ffe800'
                                }
                            ]),
                            shadowColor: 'rgba(158,135,255, 0.3)',
                            shadowBlur: 10,
                            shadowOffsetY: 20
                        },
                        itemStyle: {
                            normal: {
                                color: '#fff75e',
                                borderColor: '#fff75e'
                            }
                        }
                    },
                        {
                        name: '调拨',
                        type: 'line',
                        data:this.allotCount,
                        symbolSize: 10,
                        symbol: 'circle',
                        yAxisIndex: 0,
                        showSymbol: true,
                        lineStyle: {
                            width: 2,
                            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                                offset: 0,
                                color: '#160b7e'
                            },
                                {
                                    offset: 1,
                                    color: '#270289'
                                }
                            ]),
                            shadowColor: 'rgba(158,135,255, 0.3)',
                            shadowBlur: 10,
                            shadowOffsetY: 20
                        },
                        itemStyle: {
                            normal: {
                                color: '#160b7e',
                                borderColor: '#160b7e'
                            }
                        }
                    }
                    //     {
                    //     name: '补货',
                    //     type: 'line',
                    //     data:[34,323,775,33,22],
                    //     symbolSize: 10,
                    //     symbol: 'circle',
                    //     yAxisIndex: 0,
                    //     showSymbol: true,
                    //     lineStyle: {
                    //         width: 2,
                    //         color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                    //             offset: 0,
                    //             color: '#760cb1'
                    //         },
                    //             {
                    //                 offset: 1,
                    //                 color: '#7b2ec1'
                    //             }
                    //         ]),
                    //         shadowColor: 'rgba(158,135,255, 0.3)',
                    //         shadowBlur: 10,
                    //         shadowOffsetY: 20
                    //     },
                    //     itemStyle: {
                    //         normal: {
                    //             color: '#7b2ec1',
                    //             borderColor: '#7b2ec1'
                    //         }
                    //     }
                    // }
                    ]
                };
                myChart.setOption(option);
            }
        }
    }
</script>

<style scoped lang="scss">
    #echart{
        width:90%;
        height: 300px;
    }
    .total-card {
        cursor: pointer;
        text-align: center;
        .text1 {
            font-size: 14px;
            white-space: nowrap;
        }
        .text2 {
            font-size: 18px;
            white-space: nowrap;
            font-weight: 600;
            margin-top: 8px;
        }
        &:hover {
            background: #b7c3f9;
        }
    }

    .business-area {
        font-size: 15px;
        padding: 10px;
        &:hover {
            background: #f1f1f1;
        }
        .text1 {

        }
        .text2 {
            background-color: #f56c6c;
            color: white;
            padding: 0 10px;
            line-height: 18px;
            font-size: 12px;
            border-radius: 10px;
        }
    }
</style>