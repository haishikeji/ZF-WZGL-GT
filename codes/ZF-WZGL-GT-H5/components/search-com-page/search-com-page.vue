<template>
	<view>
		<u-navbar :title="title" title-size="36" :background="{background:type==1?'white':'#1677FF'}"
			:title-color="type==1?'#333333':'white'" :back-icon-color="type==0?'white':'#1677FF'"
			:border-bottom="false">
			<view slot="right" class="title-right">
				<slot name="topbtn"></slot>
			</view>
		</u-navbar>
		<view class="search-area" :style="{background:type==1?'white':'#1677FF'}">
			<view class="subtitle" v-if="type==0" :style="{color:type==1?'#999':'white'}">{{subtitle}}</view>
			<view class="flex-n fac">
				<view v-if="!diy" class="input-area flex-n fac" :style="{border: type==1?'1px solid #1677FF':'none'}">
					<icon type="search" size="20" />
					<u-input class="input" v-model="value" type="text" :height="97" :placeholder="placeholder" />

				</view>
				<slot name="input"></slot>
				<image class="right-btn" v-if="type==0" src="../../common/img/more.png" @click="lookSearch">
					<image class="right-btn" v-else src="../../common/img/more2.png" @click="lookSearch">
			</view>

		</view>
		<z-popup :visible.sync="show" @submit="$emit('submit');show=false" @resetForm="$emit('resetForm')" >
			<view slot="top">
				<slot name="search"></slot>
			</view>
		</z-popup>
	</view>
</template>

<script>
	export default {
		name: "search-com-page",
		props: {
			title: String,
			diy: Boolean,
			type: {
				default: 0,
				type: Number | String
			},
			subtitle: {
				default: '数据筛选',
				type: String
			},
			// value: String,
			placeholder: String,
			// isBack:{
			// 	default:true,
			// 	type:Boolean
			// },
			background: {
				default: '#1677FF',
				type: String
			},
			titleColor: {
				default: 'white',
				type: String
			},
			subtitleColor: {
				default: 'white',
				type: String
			},
			backIconColor: {
				default: 'white',
				type: String
			},
		},
		data() {
			return {
				customStyle: {
					width: '263rpx'
				},
				value: '',
				show: false
			};
		},
		watch: {
			value(newval, oldval) {
				console.log(newval)
				this.$emit('searchChange', newval)
			},

		},
		methods: {
			lookSearch() {
				this.show = true;
				this.$emit('openSearch')
			}
		}
	}
</script>

<style scoped lang="scss">
	.title-right {
		margin-right: 30rpx;
		color: white;
	}

	.search-area {
		background: #1677FF;
		width: 100vw;
		// height: 242rpx;
		color: white;
		padding: 35rpx 40rpx 40rpx 24rpx;

		.subtitle {
			font-size: 30rpx;
			margin-bottom: 24rpx;
		}

		.input-area {
			background: white;
			height: 97rpx;
			width: 601rpx;
			padding: 0 26rpx;
			border-radius: 16rpx;


			.input {
				margin-left: 20rpx;
			}
		}

		.right-btn {
			width: 52rpx;
			min-width: 52rpx;
			height: 52rpx;
			margin-left: 43rpx;
		}
	}

	.btn-area {
		padding: 40rpx 40rpx 80rpx 40rpx;
	}

	.scroll-Y {
		height: calc(100% - 280rpx);
		width: 540rpx;
		padding: 60rpx 50rpx 20rpx 50rpx;
	}
</style>
