<template>
	<view>
		<u-navbar  back-icon-color="#1677FF" title="通过原因"  title-size="36"></u-navbar>
		<z-card class="mt-24" title="编辑通过申请原因">
			<u-input class="mt-24" :clearable="false"  v-model="approveContent" type="textarea" border height="310" placeholder="请输入通过原因"/>
			<view class="mt-40 flex-n fac fjb">
				<u-button :custom-style="customStyle1" @click="submit(0)">不写理由</u-button>
				<u-button type="primary" :custom-style="customStyle2" @click="submit(1)">确认通过</u-button>
			</view>
		</z-card>
			<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
			approveContent: '',
			customStyle1: {
				width: '309rpx',
				height: '98rpx',
				fontSize: '36rpx',
			},
			customStyle2: {
				width: '309rpx',
				height: '98rpx',
				fontSize: '36rpx'
			},
			id: ''
			}
		},
		onLoad(options) {
			this.id = options.id;
		},
		methods: {
			submit(state) {
				this.$axios.post('/workflow/adopt', {
					id: this.id,
					approveContent: state == 0 ? '' : this.approveContent
				}).then(res => {
					this.$refs.uToast.show({
						title: '该任务已被接受',
						type: 'success',
						back:true
					})
				}).catch(err => {
				this.$refs.uToast.show({
					title: err.msg,
					type: 'error'
				})
				})
			}
		}
	}
</script>
<style>
	page {
		background: #F5F5F5;
	}
</style>

