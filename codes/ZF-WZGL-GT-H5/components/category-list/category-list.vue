<template>
	<view>

		<u-popup class="popup" v-model="visible" mode="left" border-radius="16" width="640rpx" :mask-close-able="false">
			<view style="margin-top: 40rpx;"></view>
			<view class="title">物资类别</view>
			<scroll-view scroll-y="true" class="scroll-Y">
				<ly-tree ref="tree" :tree-data="treeData" :showRadio="true" :ready="ready" :props="props" node-key="id"
					@radio-change="handleRadioChange">
				</ly-tree>
			</scroll-view>
		</u-popup>
		<view class="mask-close" v-if="visible" @click="closeForm"></view>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	import LyTree from '@/components/ly-tree/ly-tree.vue'
	export default {
		components: {
			LyTree
		},
		name: "category-list",
		props: {
			visible: Boolean,
			id:String,
		},
		data() {
			return {
				customStyle: {
					width: '263rpx'
				},
				ready: false, // 这里用于自主控制loading加载状态，避免异步正在加载数据的空档显示“暂无数据”
				treeData: [],
				props: {
					label: 'typeName',
					children: 'childs'
				},
			};
		},
		watch:{
			visible(newval,oldval){
				if(newval){
					this.getGoodTree();
				}
			}
		},
		methods: {
			getGoodTree() {
				this.$axios.post('/goodType/goodTypeTree', {
					"projectId": app.globalData.projectId
				}).then(res => {
					console.log(res.data.list)
					this.treeData = res.data.list;
					this.ready = true;
					console.log(this.id)
					if(this.id){
						this.$refs.tree.setCheckedKeys([this.id],true);
					}
				}).catch(err => {
					uni.showToast({
						title: err.msg
					})
				})
			},
			// resetForm() {
			// 	this.$emit('resetForm');
			// },
			closeForm() {
				this.$emit('update:visible', false);
				this.$emit('submit');
			},
			// submit() {
			// 	this.$emit('submit');
			// },
			// uni-app中emit触发的方法只能接受一个参数，所以会回传一个对象，打印对象即可见到其中的内容
		
			handleRadioChange(obj) {
				console.log('handleRadioChange',obj);
				if(obj.checked){
					this.$emit('getCategoryInfo',{id:obj.data.id,name:obj.data.typeName});
				}
			}
		}
	}
</script>

<style scoped lang="scss">
	.popup {
		.title {
			margin-left: 40rpx;
			font-size: 30rpx;
			padding-left: 20rpx;
			border-left: 10rpx solid #007AFF;
			line-height: 30rpx;
			font-weight: bold;
			padding-bottom: 10rpx;
			width: fit-content;
		}
	}

	.btn-area {
		padding: 40rpx 40rpx 80rpx 40rpx;
	}

	.scroll-Y {
		height: calc(100vh - 120rpx);
		width: 540rpx;
		padding: 0rpx 50rpx 0rpx 50rpx;
	}

	.mask-close {
		position: fixed;
		right: 0;
		width: calc(100vw - 640rpx);
		height: 100vh;
		top: 0;
		z-index: 10074;
	}
</style>
