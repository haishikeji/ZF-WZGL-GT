<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(type==0?'新增':'编辑')+'检测记录'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit">保存</u-button>
		</u-navbar>
		<view class="form mt-24">
			<u-form :model="form" ref="uForm" label-width="200rpx" :border-bottom="false" :error-type="['toast']">
				<u-form-item label="检测数量" prop="checkNum">
					<view class="flex-n fje" style="width: 100%;">
						<u-number-box height="60" input-width="160" v-model="form.checkNum"></u-number-box>
					</view>
					</z-select>
				</u-form-item>
				<!-- 		<u-form-item label="检测人">
				<u-input :border="false" height="60" input-align="right" v-model="form.name" placeholder="请输入检测人" :clearable="false" />
				</u-form-item> -->
				<u-form-item label="日期" prop="checkTime" :border-bottom="false">
					<view class="date-select flex-n fac">
						<u-input class="input" input-align="right" :border="false" height="60" v-model="form.checkTime"
							placeholder="请选择日期" :clearable="false" @click="showDate=true" />
						<image v-if="form.checkTime" class="close" mode="widthFix"
							src="../../../../common/img/index/close.png" @click="$set(form,'checkTime','')"></image>
					</view>
				</u-form-item>
			</u-form>

		</view>
		<u-toast ref="uToast"></u-toast>
		<u-calendar v-model="showDate" @change="changeDate"></u-calendar>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				showDate: false,
				id: '',
				type: 0,
				form: {
					checkNum: 0,
					checkTime: '',
				},
				rules: {
					checkNum: [{
						required: true,
						message: '',
						type: 'number',
						trigger: ['change', 'blur']
					}, {
						min: 1,
						message: '检测数量需要大于0',
						type: 'number',
						trigger: ['change', 'blur']
					}, ],

					checkTime: [{
						required: true,
						message: '',
						trigger: ['change', 'blur']
					}]
				},

			}
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		onLoad(options) {
			console.log(options)
			this.id = options.id;
			this.type = options.type;
			if (this.type == 1) {
				this.getData();
			}
		},
		methods: {
			changeDate(e) {
				console.log(e)
				this.$set(this.form, 'checkTime', e.result)
			},
			getData() {
				this.$axios.get('/goodCheck/findLogById/' + this.id).then(res => {
					console.log(res.data);
					this.form = res.data.list[0];
				}).catch(err => {

				})
			},
			submit() {

				this.$refs.uForm.validate((valid) => {
					if (valid) {
						if (this.type == 0) {
							this.addSubmit();
						} else {
							this.editSubmit();
						}
					} else {

						return false;
					}
				});
			},
			addSubmit() {
				var form = this.form;
				console.log(this.form)
				this.$axios.post('/goodCheck/save', {
					"projectId": app.globalData.projectId,
					"goodId": this.id,
					"checkNum": form.checkNum,
					"checkTime": form.checkTime
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增检测记录成功',
						type: 'success',
						back: true,
					})
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			editSubmit() {
				var form = this.form;
				this.$axios.post('/goodCheck/update', {
					"id": this.id,
					"checkNum": form.checkNum,
					"checkTime": form.checkTime
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑检测记录成功',
						type: 'success',
						back: true,
					})
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
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
	.form {
		padding: 0 24rpx;
		background: white;
	}

	.scroll {
		height: calc(100vh - 400rpx);
	}

	.date-select {
		position: relative;
		width: 100%;

		.close {
			width: 40rpx;
			margin-left: 20rpx;
		}
	}
</style>
