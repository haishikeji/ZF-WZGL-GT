<template>
	<view class="material">
		<!-- <u-navbar  back-icon-color="#1677FF" title="选择物资"  title-size="36"></u-navbar> -->
		<search-com-page title="定测量物资选择" diy :type="1" @submit="searchList" @resetForm="resetForm">
			<view slot="input" style="width: 100%;background: white;border-radius: 8rpx;">
				<z-select :clearable="false" style="width: 100%;" placeholder="请选择物资类别" v-model="goodTypeName"
					@click="chooseMutil">
				</z-select>
			</view>
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<!-- <u-form-item label="物资类别" prop="intro" :border-bottom="false">
						<z-select  :clearable="false" style="width: 100%;" placeholder="请选择物资类别" v-model="form.mutilvalue" @click="chooseMutil">
						</z-select>
						</u-form-item> -->
					<u-form-item label="物资编号" :border-bottom="false">
						<u-input border height="95" v-model="goodCode" placeholder="请输入物资编号" :clearable="false" />
					</u-form-item>
					<u-form-item label="物资名称" :border-bottom="false">
						<u-input border height="95" v-model="goodName" placeholder="请输入物资名称" :clearable="false" />
					</u-form-item>
				</u-form>
			</view>

		</search-com-page>
		<z-card :class="[chooseList.indexOf(item.goodId)>-1?'material_check':'']" class="mt-20" v-for="(item,index) in list"
			:key="index" @click.native="selectMaterial(item.goodId)">
			<u-row gutter="16">
				<!-- <u-col span="3">
					<view></view>
				</u-col>
				<u-col span="9" text-align="right">
					<view class="detail" @click.stop="lookDetail(item.id)">查看详情</view>
				</u-col> -->
				<!-- <u-col span="3" class="mt-20">
					<view>项目名称</view>
				</u-col>
				<u-col span="9" class="mt-20" text-align="right">
					<view>这个没有</view>
				</u-col> -->
				<u-col span="3" class="mt-20">
					<view>物资编号</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.goodCode}}</view>
				</u-col>
				<u-col class="mt-20" span="3">
					<view>物资名称</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.goodName}}</view>
				</u-col>
				<!-- <u-col span="3" class="mt-20">
					<view>供应商名称</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>没有返回</view>
				</u-col> -->
				<u-col class="mt-20" span="3">
					<view>规格型号</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.specsModelName}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>计量单位</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.measureUnit}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>专业</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.majorName}}</view>
				</u-col>
				
				<!-- <u-col span="3" class="mt-20">
					<view>合计</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.num}}</view>
				</u-col> -->

			</u-row>
		</z-card>
		<u-popup v-model="showDetail" mode="center">
			<z-card title="定测量详情">
				<view class="comm-detail-text detail-info">

					<view>物资编号：</view>
					<view class="right">12034131310000</view>
					<view>物资名称：</view>
					<view class="right">12034131310000</view>
					<view>单位：</view>
					<view class="right">12034131310000</view>
					<view>规格型号：</view>
					<view class="right">12034131310000</view>
					<view>北京--盛世：</view>
					<view class="right">12034131310000</view>
					<view>北京--盛世：</view>
					<view class="right">12034131310000</view>
					<view>合计：</view>
					<view class="right">12034131310000</view>
					<view>制单人：</view>
					<view class="right">12034131310000</view>
					<view>创建日期：</view>
					<view class="right">12034131310000</view>
					<view>备注：</view>
					<view class="right">12034131310000</view>
				</view>
			</z-card>

		</u-popup>
		<!-- mode="mutil-column-auto" -->
		<!-- <u-select v-model="showMutil" mode="mutil-column-auto" label-name="typeName" value-name="id" :list="treeList"
			@confirm="confirmMutil" :mask-close-able="false"></u-select>
< -->
		<category-list :visible.sync="showMutil" :id="goodType" @getCategoryInfo="getCategoryInfo"></category-list>
		<z-bottom-fixed v-if="chooseList.length>0">
			<z-button type="primary" @click="submit">确定</z-button>
		</z-bottom-fixed>

		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				list: [],
				treeList: [],
				chooseList: [],
				showMutil: false,
				showDetail: false,
				form: {},
				rules: {},
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				goodCode: '',
				goodName: '',
				specsModelName: '',
				goodType: '',
				goodTypeName: '',
				pageSize: 20,
				currentPage: 1,
				total: 0,
newData: []
			}
		},
		onLoad() {
			this.newData = JSON.parse(JSON.stringify(uni.getStorageSync('materialList')));
			this.getDataList();
		},
		onReachBottom() {
			if (this.list.length == this.total) {
				this.status = 'nomore';
			} else {
				this.status = 'loading';
				this.currentPage++;
				this.getDataList();
			}
		},

		methods: {
			getCategoryInfo(info) {
				console.log(info)
				this.goodType = info.id;
				this.goodTypeName = info.name;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/good/goodList', {
					"projectId": app.globalData.projectId,
					"goodCode": this.goodCode,
					"goodName": this.goodName,
					"specsModelName": this.specsModelName,
					"filter": 1,
					goodType: this.goodType,
					pageNum: this.currentPage,
					pageSize: this.pageSize
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					for (var i in this.list) {
						this.list[i].goodId = this.list[i].id;
					}
					this.setMultiSelected();
					this.total = res.data.total;
					if (this.list.length == this.total) {
						this.status = 'nomore';
					} else {
						this.status = 'loadmore';
					}
				}).catch(err => {
					this.status = 'loadmore';
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			setMultiSelected() {
				console.log(this.newData, this.list)
				var multipleSelection = [];
				for (var i in this.list) {
					console.log(this.list[i].goodId, this.newData)
					for (var j in this.newData) {
						console.log(this.newData[j].goodId)
						if (this.newData[j].goodId == this.list[i].goodId) {
							console.log('存在', this.list[i].goodId)
							multipleSelection.push(this.list[i].goodId)
			
							break;
						}
					}
				}
				this.chooseList=multipleSelection;
			},
			lookDetail(id) {
				this.showDetail = true;
			},
			chooseMutil() {
				this.showMutil = true;
			},
			confirmMutil(data) {
				console.log(data)
				this.$set(this.form, 'mutilvalue', data[0].label + '/' + data[1].label + '/' + data[2].label);
			},
			selectMaterial(id) {
				console.log(id)
				var num = this.chooseList.indexOf(id);
				console.log(num)
				if (num > -1) {
					this.chooseList.splice(num, 1);
				} else {
					this.chooseList.push(id);
				}
			},
			submit() {
				var list = [];
				for (var i in this.chooseList) {
					for (var j in this.list) {
						if (this.list[j].id == this.chooseList[i]) {
							list.push(this.list[j]);
							break;
						}
					}
				}
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]
				// 触发上一页 upData 函数(并携带参数)
				prevPage.$vm.upData(list)
				uni.navigateBack({
					delta: 1
				})
			},
			resetForm() {
				this.goodCode = '';
				this.goodName = '';
				this.goodType = '';
			},
		}

	}
</script>
<style>
	page {
		background: #F5F5F5;
	}
</style>
<style scoped lang="scss">
	.material_check {
		border: 1px solid #1677FF;
	}

	.material {
		.item {
			color: #ccc;
		}
	}

	.detail {
		color: #1677FF;
	}

	.detail-info {
		padding: 20rpx;
		border: 1px solid #eee;
		margin-top: 24rpx;
		border-radius: 6rpx;
	}
</style>
