<template>
  <div class="header flex-n fac fjb">
    <div class="left flex-n fac">
      <div class="title">物资管理系统</div>
      <div
        class="spread"
        :class="$store.state.showMenu ? 'el-icon-s-fold' : 'el-icon-s-unfold'"
        @click="$store.state.showMenu = !$store.state.showMenu"
      ></div>
    </div>
    <div class="right flex-n fac">
      <el-select
        v-model="chooseProject"
        placeholder="请选择"
        size="small"
        @change="handleProject"
      >
        <el-option
          v-for="item in projectList"
          :key="item.id"
          :label="item.projectName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <div class="name">
        {{ $root.getStorage("info").userName || "" }}
      </div>
      <div class="icon_1 el-icon-switch-button" @click="exit"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "layoutheader",
  data() {
    return {
      projectList: [],
      chooseProject: "",
    };
  },
  mounted() {
    this.getProject();
  },
  watch: {
    "$store.state.changeProject"(newval, oldval) {
      if (newval) {
        this.getProject();
      }
    },
  },
  methods: {
    handleProject(data) {
      console.log(data);
      this.$root.setStorage("projectId", data);
      location.reload();
      this.$axios
        .post("/project/updateIsdefault", {
          id: data,
        })
        .then((res) => {})
        .catch((err) => {
          this.$message.error(err.msg);
        });
    },
    //获取项目
    getProject() {
      this.$axios
        .get("/user/porjectDropdown/" + this.$root.getStorage("userId"))
        .then((res) => {
          console.log("项目列表", res.data.list);
          this.projectList = res.data.list;
          for (var i in res.data.list) {
            if (res.data.list[i].isdefault == "1") {
              this.chooseProject = res.data.list[i].id;
              this.$root.setStorage("projectId", res.data.list[i].id);
              break;
            }
          }
          // this.chooseProject=res.data.list[0].id;
          // this.$root.setStorage("projectId",res.data.list[0].id)
          this.$store.state.changeProject = false;
        })
        .catch((err) => {
          this.$message.error(err.msg);
        });
    },
    exit() {
      this.$router.replace("/");
      localStorage.clear();
    },
  },
};
</script>

<style scoped lang="scss">
.header {
  background: #409eff;
  height: 100% !important;
  padding: 0 20px;
  .left {
    .title {
      font-size: 20px;
      //   font-weight: bold;
      margin: 0 5px;
      color: white;
    }
    .spread {
      width: 60px;
      height: 50px;
      margin-left: 20px;
      color: #fff;
      text-align: center;
      line-height: 50px;
      font-size: 24px;
      border: {
        // left: 1px solid #eee;
        // right: 1px solid #eee;
      }
      &:hover {
        // background: #f1f1f1;
        cursor: pointer;
      }
    }
  }
  .right {
    .icon_1 {
      width: 24px;
      height: 24px;
      font-size: 24px;
      color: white;
      cursor: pointer;
      margin: 0 20px 0 0;
    }
  }
  .name {
    margin: 0 10px;
    color: #fff;
  }
}
</style>
