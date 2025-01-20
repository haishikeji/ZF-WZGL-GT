<template>
	<view class="informantion_mask" @tap="closeWindow">
		<view class="informantion_content" @tap.stop.prevent>
			<view class="mask-header">
					<view style="padding: 20rpx; width: 100%; height: 40rpx; padding-top: 40rpx;">
						<view class="input-area flex-n fac" style=" border-bottom:  1px solid #1677FF ; ">
							<icon type="search" size="20" />
							<u-input class="input" v-model="value" type="text"  placeholder="请输入区间名称" @input />
						</view>
						<scroll-view scroll-y="true" class="scroll-Y" style="padding-top: 10rpx; margin: 10rpx;"> 
							<!-- <view style="height: 650rpx; padding: 10rpx;"> -->
								<z-card  style=" width: 95%; " 
								:class="[chooseList.indexOf(item.id)>-1?'material_check':'']"  v-for="(item,index) in restaurants"
									:key="index" @click.native="selectMaterial(item.id)">
									<view style="text-align: center;">
										<view>{{item.stationIntervalName}}</view>
									</view>
								</z-card>
						</scroll-view>
					</view>
			</view>
			
			<view class="informantion-btn" style="padding-top: 20rpx;">
				<button type="primary" @click="submitE" >确认</button>
			</view>
		</view>
	</view>
</template>
 
<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				blogPopupTags:[],
				stationIntervalList:[],
				restaurants:[],
				chooseList:[],
				value:''
			}
		},
		onLoad() {
			this.blogPopupTags = uni.getStorageSync('blogPopupTags');
			this.getStationIntervalList();
		},
		
		watch: {
			value(newval, oldval) {
				this.querySearch()
			},
		
		},
		methods: {
			
			submitE(){
				var list =  this.stationIntervalList.filter( si => this.chooseList.includes(si.id));
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]
				// 触发上一页 upData 函数(并携带参数)
				prevPage.$vm.upStationIntervalData(list)
				uni.navigateBack({
					delta: 1
				})
				this.blogPopupTags = [];
				this.stationIntervalList = [];
				this.restaurants = [];
				this.chooseList = [];
				this.value = '';
			},
			getStationIntervalList() {
				this.$axios.post('/stationInterval/findList', {
					"belongProjectId": app.globalData.projectId,
				}).then(res => {
					var stationIntervalIdsList =  [];
					for(var i in this.blogPopupTags){
						stationIntervalIdsList.push(this.blogPopupTags[i].id)
					}

					this.stationIntervalList = res.data.list.filter( si => !stationIntervalIdsList.includes(si.id));
					this.restaurants = res.data.list.filter( si => !stationIntervalIdsList.includes(si.id));
				}).catch(err => {
					console.log(err)
					console.log(err.msg)
				})
			},
			querySearch() {
					var restaurants = this.stationIntervalList;
					this. restaurants = this.value ? restaurants.filter(this.createFilter()) : restaurants;
			},
			createFilter() {
				return (restaurant) => {
					return (restaurant.stationIntervalName.includes(this.value));
				};
			},
			selectMaterial(id) {
				var num = this.chooseList.indexOf(id);

				if (num > -1) {
					this.chooseList.splice(num, 1);
				} else {
					this.chooseList = [];
					this.chooseList.push(id);
				}
			},
			//点击mask消失，由于是页面实际相当于返回
			closeWindow() {
				uni.navigateBack({
					delta: 1
				})
			}
		}
	}
</script>
 
<style lang="scss">
	page {
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, 0.4);
	}
 
	.informantion_mask {
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}
 
	.informantion_content {
		width: 600rpx;
		height: 820rpx;
		overflow: hidden;
		border-radius: 10rpx;
		background-color: white;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		padding-bottom: 20rpx;
	}
 
	// .mask-header {
	// 	height: 400rpx;
	// 	position: relative;
	// 	// background-image: url('../../static/tabbar-1.png');
	// 	background-repeat: no-repeat;
	// 	background-size: cover;
	// 	image {
	// 		width: 40rpx;
	// 		height: 40rpx;
	// 		position: absolute;
	// 		top: 20rpx;
	// 		right: 20rpx;
	// 	}
	// }
	
	.informantion-title {
		text-align: center;
		padding: 0 40rpx;
		p {
			font-size: 35rpx;
			font-weight: bold;
			line-height: 80rpx;
		}
		span {
			color: #6C6C6C;
		}
	}
 
	.informantion-btn {
		height: 93rpx;
		width: 80%;
		margin: 0 auto;
	}
	.material_check {
		border: 1px solid #1677FF;
	}
	
	.material {
		.item {
			color: #ccc;
		}
	}
	
	.scroll-Y {
		height: 600rpx;
		// width: 540rpx;
		// padding: 0rpx 50rpx 0rpx 50rpx;
	}
</style>