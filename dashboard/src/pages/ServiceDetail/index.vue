<template>
  <div class="serviceDetail">
    <div class="header">
      <div class="back" @click="router.go(-1)">
        <LeftOutlined />
      </div>
      <div class="appName">
        {{ appName }}
      </div>
      <div class="ip">
        {{ ip }}
      </div>
    </div>
    <main>
      <div class="search">
        <Input placeholder="请输入搜索内容" @change="handleSearch" />
      </div>
      <div class="result">
        <MethodCard
          v-for="item in displayData"
          :key="item.id"
          :data="item"
          @openMethodDrawer="handleOpenMethodDrawer(item)"
        />
      </div>
    </main>
  </div>
  <MethodDrawer
    v-if="methodDrawerVisible && methodItem"
    :visible="methodDrawerVisible"
    :methodItem="methodItem"
    @close="hanldeMethodClose"
  />
</template>
<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";
import { LeftOutlined } from "@ant-design/icons-vue";
import { Input } from "ant-design-vue";
import { ref } from "vue";
import type { ChangeEvent } from "ant-design-vue/es/_util/EventInterface";

import mockData from "./mock.js";
import MethodCard from "./components/MethodCard.vue";
import MethodDrawer from "./components/MethodDrawer.vue";

const route = useRoute();
const router = useRouter();
const { ip, appName } = route.query || { ip: "", appName: "" };

const orginalData = mockData;
const displayData = ref(mockData);
const methodDrawerVisible = ref(false);
const methodItem = ref<any>(null);

const handleSearch = (e: ChangeEvent) => {
  const value = e.target.value;
  displayData.value = orginalData.filter((item: any) => {
    const { beanName, methodDesc, methodSignature } = item;

    return (
      beanName.includes(value) ||
      methodDesc.includes(value) ||
      methodSignature.includes(value)
    );
  });
};

const handleOpenMethodDrawer = (item: any) => {
  methodDrawerVisible.value = true;
  methodItem.value = item;
};

const hanldeMethodClose = () => {
  methodDrawerVisible.value = false;
  methodItem.value = null;
};
</script>
<style scoped lang="scss">
@import "@/style/variable.scss";

.serviceDetail {
  background-color: white;
  .header {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 12px;
    border-bottom: 1px solid $divider-color;

    .back {
      font-size: 20px;
      cursor: pointer;
    }
    .appName {
      font-size: 24px;
    }
    .ip {
      font-size: 16px;
    }
  }
  main {
    padding: 16px;
    .search {
      width: 200px;
    }
    .result {
      margin-top: 12px;
      display: flex;
      flex-wrap: wrap;
    }
  }
}
</style>
