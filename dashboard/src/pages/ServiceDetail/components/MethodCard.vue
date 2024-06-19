<template>
  <div class="methodCard">
    <Card :title="data.methodSignature" hoverable>
      <div class="beanName">{{ data.beanName }}</div>
      <div class="methodDesc">{{ data.methodDesc }}</div>

      <template #actions>
        <Tooltip title="点击执行">
          <PlayCircleOutlined @click="emits('openMethodDrawer')" />
        </Tooltip>
      </template>
    </Card>
  </div>
</template>

<script lang="ts" setup>
import { Card, Tooltip } from "ant-design-vue";
import { PlayCircleOutlined } from "@ant-design/icons-vue";

defineProps<{
  data: any;
}>();

const emits = defineEmits(["openMethodDrawer"]);
</script>

<style scoped lang="scss">
.methodCard {
  width: 300px;
  margin-right: 12px;
  margin-bottom: 12px;
  .beanName {
    font-size: 12px;
    color: #999;
  }
}
</style>
