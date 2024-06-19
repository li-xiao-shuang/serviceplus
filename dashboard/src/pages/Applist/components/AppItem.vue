<template>
  <div class="appListInstance">
    <div class="appName" @click="toServiceDetail()">
      <img src="@/assets/servicePlus.jpg" alt="" />
      {{ props.appName }}
    </div>
    <div class="ipList">
      <div
        v-for="ip in props.ips"
        :key="ip"
        class="ipItem"
        @click="toServiceDetail(ip)"
      >
        {{ ip }}
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps<{
  appName: string;
  ips: string[];
}>();

const toServiceDetail = (ip?: string) => {
  router.push({
    path: "/serviceDetail",
    query: { ip: ip || props.ips[0], appName: props.appName },
  });
};
</script>
<style scoped lang="scss">
@import "@/style/variable.scss";

.appListInstance {
  .appName {
    display: flex;
    align-items: center;
    cursor: pointer;
    &:hover {
      color: #0070cc;
    }

    img {
      width: 40px;
      height: 40px;
      margin-right: 10px;
    }
    font-size: 20px;
  }
  .ipList {
    padding: 12px;
    height: 120px;
    overflow: auto;
    margin-top: 12px;
    background-color: #35a7ff1f;
    .ipItem {
      height: 30px;
      padding: 4px;
      box-sizing: border-box;
      margin-bottom: 6px;
      cursor: pointer;
      &:hover {
        border: 1px solid $color4;
        border-radius: 4px;
      }
    }
  }
}
</style>
