<template>
  <Drawer
    :open="visible"
    @close="emits('close')"
    :title="methodItem.methodSignature"
    width="80%"
  >
    <template #extra>
      <Button type="primary">运行</Button>
    </template>

    <div class="params">
      <div class="method-sub-title">参数</div>
      <Table
        :columns="columns"
        :dataSource="methodItem.params"
        :pagination="false"
      >
        <template #bodyCell="{ column }">
          <template v-if="column.dataIndex === 'value'">
            <Input></Input>
          </template>
        </template>
      </Table>
    </div>
    <div class="return">
      <div class="method-sub-title">返回</div>
      <div class="returnResult">
        <Divider v-if="!result"> 点击右上角执行方法 </Divider>
        <VueJsonPretty
          v-else
          :data="result"
          selectableType="single"
          showLineNumber
          showLine
          showLength
        />
      </div>
    </div>
  </Drawer>
</template>

<script lang="ts" setup>
import { Drawer, Table, Input, Button, Divider } from "ant-design-vue";
import VueJsonPretty from "vue-json-pretty";
import "vue-json-pretty/lib/styles.css";

import result from "../result.json";

defineProps<{
  visible: boolean;
  methodItem: any;
}>();

const emits = defineEmits(["close"]);

const columns = [
  {
    title: "参数名",
    dataIndex: "desc",
  },
  {
    title: "参数类型",
    dataIndex: "type",
  },
  {
    title: "参数值",
    dataIndex: "value",
  },
];
</script>

<style scoped lang="scss">
.params {
  margin-bottom: 12px;
}
.method-sub-title {
  font-size: 20px;
  padding: 10px 12px;
  position: relative;
  margin-bottom: 12px;
  &::after {
    content: "";
    width: 4px;
    height: 50%;
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    background-color: #4080ff;
    border-radius: 0 4px;
  }
}
</style>
