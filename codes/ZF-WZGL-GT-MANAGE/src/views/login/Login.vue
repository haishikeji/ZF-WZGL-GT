<template>
  <div class="login">
    <div class="content">
      <div class="content-item flex-n fjc">
        <img class="logo" src="../../assets/logo.png" />
        <div>
          <div class="name">物资管理系统</div>
          <div class="mt-20">
            <el-form
              :model="info"
              :rules="rules"
              ref="ruleForm"
              label-width="0"
              size="medium"
              :show-message="false"
            >
              <div class="flex-n">
                <el-form-item label="" prop="loginName">
                  <el-input
                    maxlength="32"
                    class="w-300"
                    v-model="info.loginName"
                    placeholder="请输入用户名称"
                  ></el-input>
                </el-form-item>
                <el-form-item label="" class="ml-20" prop="passWord">
                  <div class="flex-n fac">
                    <el-input
                      maxlength="32"
                      class="w-300"
                      type="password"
                      v-model="info.passWord"
                      placeholder="请输入登录密码"
                    ></el-input>
                    <el-button
                      class="ml-20"
                      icon="el-icon-right"
                      circle
                      @click="login('ruleForm')"
                    ></el-button>
                  </div>
                </el-form-item>
              </div>
            </el-form>
          </div>
        </div>
      </div>
      <div class="content-item flex-n fac fjc"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      info: {
        loginName: "",
        passWord: "",
      },
      rules: {
        passWord: [{ required: true, message: "", trigger: "blur" }],
        loginName: [{ required: true, message: "", trigger: "blur" }],
      },
    };
  },
  mounted() {
    if (this.$root.getStorage("authorization")) {
      this.$router.push("/index");
    }
  },
  methods: {
    login(formName) {
      var info = this.info;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post("/account/login", {
              loginName: info.loginName,
              passWord: info.passWord,
            })
            .then((res) => {
              console.log(res);
              this.$message.success("登录成功");
              this.$root.setStorage("login", true);
              this.$store.state.dicChange = true;
              this.$root.setStorage("authorization", res.data.Authorization);
              if (res.data.roleId == 1) {
                this.$root.setStorage("isManage", true);
              } else {
                this.$root.setStorage("isManage", false);
              }

              this.$root.setStorage("info", res.data);
              this.$root.setStorage("userId", res.data.userId);
              this.$router.replace("/index");
            })
            .catch((err) => {
              console.log(err);
              this.$message.error(err.msg);
            });
        } else {
          return false;
        }
      });
      // this.$router.push('/index')
    },
  },
};
</script>

<style scoped lang="scss">
.login {
  width: 100%;
  height: 100vh;
  background: #62bff5;
  position: relative;
  .content {
    position: absolute;
    height: 40vh;
    width: 100%;
    top: 30vh;
    background: rgba(0, 0, 0, 0.5);
    .content-item {
      margin-top: 5vh;
      .logo {
        width: 60px;
        height: fit-content;
        margin-top: 20px;
        margin-right: 20px;
      }
      .name {
        color: white;
        font-weight: bold;
        font-size: 40px;
      }
      .ename {
        color: white;
        font-size: 30px;
      }
    }
  }
}
</style>
