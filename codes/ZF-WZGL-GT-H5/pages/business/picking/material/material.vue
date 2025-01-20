<template>
	<view class="material">
		<!-- <u-navbar  back-icon-color="#1677FF" title="选择物资"  title-size="36"></u-navbar> -->
		<search-com-page title="选择物资" diy :type="1" @submit="submit" @resetForm="resetForm">
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

					<u-form-item label="规格型号" :border-bottom="false">
						<u-input border height="95" v-model="specsModelName" placeholder="请输入规格型号" :clearable="false" />
					</u-form-item>
				</u-form>
			</view>

		</search-com-page>
		<z-card v-if="type==0" :class="[choose==item.id?'material_check':'']" class="mt-20 item"
			v-for="(item,index) in list" :key="index" @click.native="selectSimMaterial(item.id)">
			<u-row gutter="16">
				<u-col span="3">
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
					<view>库存数量</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.stockNum}}</view>
				</u-col>
			</u-row>
		</z-card>

		<z-card v-if="type==1"
			:class="[chooseList.indexOf(item.goodId)>-1?'material_check':'',!canSelect&&item.stockNum==0?'disabled_select':'']"
			class="mt-20" v-for="(item,index) in list" :key="index"
			@click.native="!canSelect&&item.stockNum==0?'':selectMaterial(item.goodId)">
			<u-row gutter="16">
				<u-col span="3">
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
					<view>库存数量</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.stockNum}}</view>
				</u-col>

				<template v-if="state==1">
					<u-col span="3" class="mt-20">
						<view>仓库</view>
					</u-col>
					<u-col span="9" text-align="right">
						<view>11</view>
					</u-col>
					<u-col span="3" class="mt-20">
						<view>库位</view>
					</u-col>
					<u-col span="9" text-align="right">
						<view>11</view>
					</u-col>
				</template>
				<template v-if="state==2">
					<u-col span="3" class="mt-20">
						<view>备注</view>
					</u-col>
					<u-col span="9" text-align="right">
						<view>{{item.remarks}}</view>
					</u-col>
				</template>
			</u-row>
		</z-card>
		<category-list :visible.sync="showMutil" :id="goodType" @getCategoryInfo="getCategoryInfo"></category-list>
		<z-bottom-fixed v-if="choose||chooseList.length>0">
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
				choose: '',
				chooseList: [],
				type: 0, //0 单选 1多选
				state: 0, //显示的内容控制
				showType: false,
				showMutil: false,
				canSelect: false,
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

				newDataInfo: {},
				newData: []
			}
		},
		onLoad(options) {
			this.type = Number(options.type);
			if (this.type == 0) {
				this.newDataInfo = JSON.parse(JSON.stringify(uni.getStorageSync('materialInfo')));
			} else {
				this.newData = JSON.parse(JSON.stringify(uni.getStorageSync('materialList')));
			}
			if (options.state)
				this.state = Number(options.state);
			this.canSelect = options.canSelect == 'true' ? true : false;
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
					goodType: this.goodType,
					pageNum: this.currentPage,
					pageSize: this.pageSize
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					for (var i in this.list) {
						this.list[i].goodId = this.list[i].id;
					}
				
					// this.setMultiSelected();
					this.total = res.data.total;
					if (this.list.length == this.total) {
						this.status = 'nomore';
					} else {
						this.status = 'loadmore';
					}
					if (this.type == 0) {
						this.setRadioSelected();
					} else {
						this.setMultiSelected();
					}
				}).catch(err => {
					this.status = 'loadmore';
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			//单选反显
			setRadioSelected() {
				console.log(this.list)
				for (var i in this.list) {
					if (this.list[i].goodId == this.newDataInfo.goodId) {
						this.choose = this.newDataInfo.goodId;
						break;
					}
				}
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
			chooseType() {
				this.showType = true;
			},
			confirmType(data) {
				console.log(data)
				this.$set(this.form, 'type', data[0].label);
			},
			chooseMutil() {
				this.showMutil = true;
			},
			confirmMutil(data) {
				console.log(data)
				this.$set(this.form, 'mutilvalue', data[0].label + '/' + data[1].label + '/' + data[2].label);
			},
			selectSimMaterial(id) {
				console.log(id)
				if (this.choose != id) {
					this.choose = id;
				} else {
					this.choose = '';
				}
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
				//多选
				if (this.type) {
					var list = [];
					for (var i in this.chooseList) {
						for (var j in this.list) {
							if (this.list[j].goodId == this.chooseList[i]) {
								list.push(this.list[j]);
								break;
							}
						}
					}
					let pages = getCurrentPages();
					let prevPage = pages[pages.length - 2]
					// 触发上一页 upData 函数(并携带参数)
					prevPage.$vm.upData(list)
				} else {
					var data = {};
					for (var i in this.list) {
						if (this.list[i].id == this.choose) {
							data = this.list[i];
						}
					}
					let pages2 = getCurrentPages();
					let prevPage2 = pages2[pages2.length - 2]
					// 触发上一页 upData 函数(并携带参数)
					prevPage2.$vm.upData(data)
				}
				uni.navigateBack({
					delta: 1
				})
			},
			resetForm() {
				this.$refs.uForm.resetFields();
			},
		},

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
			color: #333;
		}
	}

	.disabled_select {
		background: #f1f1f1;
	}
</style>
