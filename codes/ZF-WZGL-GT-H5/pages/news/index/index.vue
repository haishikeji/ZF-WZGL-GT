<template>
	<view>
		<u-navbar class="topbar" :is-back="false" title-color="white" title-size="36" title="我的消息"
			:background="{background:'#1677FF'}" :border-bottom="false">
			<!-- <image slot="right" class="img" src="../../common/img/more.png" @click="search"></image> -->
		</u-navbar>
		<u-tabs :list="msgTypeList" name="codeName" bar-height="4" font-size="32" height="80" bar-width="100"
			bg-color="#1677FF" active-color="white" inactive-color="#d7d7d7" is-scroll :current="current"
			@change="change"></u-tabs>
		<scroll-view class="scroll-Y" scroll-y="true" v-if="list.length>0">
			<view class="content">
				<z-card class="examine mb-30" v-for="(item,index) in list" :key="index">
					<view class="flex-n fac">
						<view>
							<view class="flex-n fac">
								<view class="title">{{item.title}}</view>
								<u-tag class="ml-10" text="已读" size="mini" mode="light" v-if="item.isRead=='1'" />
								<u-tag class="ml-10" bg-color="#FFECE3" v-else color="#FF6010" border-color="#ffc7ab" text="未读"
									size="mini" mode="light" />
							</view>

							<view class="state mt-10">消息类型：{{item.msgTypeName}}</view>
							<view class="state mt-10">来自系统超量提醒</view>
						</view>
					</view>
					<u-section class="mt-16" :bold="false" color="#999999" sub-color="#999999" :show-line="false"
						:title="item.createTime" sub-title="查看详情" @click="gotpPage"></u-section>
				</z-card>
				<u-loadmore class="mt-40" :status="status" />
			</view>

		</scroll-view>
		<image v-if="list.length==0" class="nodata" mode="widthFix" src="../../../common/img/nodata.png"></image>
		<u-toast ref="uToast"></u-toast>
		<u-tabbar :list="$store.state.tabbar" active-color="#1677FF"></u-tabbar>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				msgTypeList: [],
				list: [],
				current: 0,
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				
				pageSize: 20,
				currentPage: 1,
				total: 0,
			};
		},
		onLoad() {
			this.getMsgTypeList();
		},
	onReachBottom() {
			if(this.list.length==this.total){
				this.status='nomore';
			}else{
				this.status='loading';
				this.currentPage++;
				this.getDataList(this.msgTypeList[this.current].id);
			}
		},
		methods: {
			getMsgTypeList() {
				var msgTypeList = [];
				msgTypeList = uni.getStorageSync('dic').list.filter((item) => item.codeKind == 'msgType');
				this.msgTypeList = [{
					codeName: '全部',
					id: 'all'
				}].concat(msgTypeList);
				this.searchList(this.msgTypeList[this.current].id);
			},
			change(index) {

				this.current = index;
				this.searchList(this.msgTypeList[index].id);
			},
			searchList(msgTypeId){
				this.list=[];
				this.currentPage=1;
				this.total=0;
				this.getDataList(msgTypeId);
			},
			getDataList(msgTypeId) {
				this.$axios.post('/msg/findList', {
					"projectId": app.globalData.projectId,
					"msgTypeId": msgTypeId == 'all' ? '' : msgTypeId,
					// "beginTime": this.dateRange?this.dateRange[0]:'',
					// "endTime": this.dateRange?this.dateRange[0]:'',
					pageNum: this.currentPage,
					pageSize: this.pageSize
				}).then(res => {
				var list=res.data.list;
				this.list = this.list.concat(list) ;
					this.total = res.data.total;
					if(this.list.length==this.total){
						this.status='nomore';
					}else{
						this.status='loadmore';
					}
				}).catch(err => {
					this.status='loadmore';
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			gotpPage() {
				uni.navigateTo({
					url: '../detail/detail'
				})
			}
		}
	}
</script>
<style>
	page {
		background: white;
	}
</style>
<style scoped lang="scss">
	.scroll-Y {
		height: calc(100vh - 300rpx - var(--status-bar-height));

		.content {
			padding-bottom: 40rpx;
			padding-top: 30rpx;
		}

		.examine {
			.title {
				font-size: 30rpx;
				color: #333333;
				font-weight: 600;
			}

			.state {
				color: #999999;
				font-size: 26rpx;
			}

			.img {
				height: 96rpx;
			}
		}
	}

	.nodata {
		width: 571rpx;
		margin: 0 calc((100% - 571rpx) / 2);
		margin-top: 289rpx;
	}
</style>
