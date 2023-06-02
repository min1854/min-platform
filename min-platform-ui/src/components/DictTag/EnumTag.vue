<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.value)">
        <el-tag
          :disable-transitions="true"
          :key="item.value"
          :index="index"
        >
          {{ item.desc }}
        </el-tag>
      </template>
    </template>
  </div>
</template>

<script>
export default {
  name: "DictTag",
  props: {
    options: {
      type: Array,
      default: null,
    },
    value: [Number, String, Array, Object, Boolean],
  },
  computed: {
    values() {
      console.log(this.value, this.options)
      if (this.value !== null && typeof this.value !== 'undefined') {
        if (Array.isArray(this.value)) {
          return this.value;
        } else if (this.value instanceof Object) {
          return [this.value.value]
        } else {
          return [this.value];
        }
      } else {
        return [];
      }
    },
  },
};
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
