<template>
  <div class="appListPage">
    <div class="applistHeader">应用</div>
    <div class="applist">
      <AppItem
        v-for="item in appList"
        :appName="item"
        :key="item"
        :ips="ips"
        class="applistItem"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import AppItem from "./components/AppItem.vue";

const appList = new Array(11)
  .fill("")
  .map(() => "app" + Math.round(Math.random() * 100));

const ips = [
  "127.0.0.1",
  "127.0.0.2",
  "127.0.0.3",
  "127.0.0.4",
  "127.0.0.5",
  "127.0.0.6",
  "127.0.0.7",
  "127.0.0.8",
  "127.0.0.9",
  "127.0.0.10",
  "127.0.0.11",
];
</script>

<style lang="scss" scoped>
@import "@/style/variable.scss";

.appListPage {
  background-color: white;
  margin: 6px;
  box-sizing: border-box;
  .applistHeader {
    height: 44px;
    line-height: 24px;
    padding: 10px 16px;
    position: relative;
    box-sizing: border-box;
    border-bottom: 1px solid $divider-color;
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
  .applist {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    .applistItem {
      width: 25%;
      height: 228px;
      overflow: hidden;
      padding: 16px;
      border-bottom: 1px solid $divider-color;
      border-right: 1px solid $divider-color;
      box-sizing: border-box;
      transition: box-shadow 0.3s;
      &:hover {
        box-shadow: 0 1px 2px -2px rgba(0, 0, 0, 0.16),
          0 3px 6px 0 rgba(0, 0, 0, 0.12), 0 5px 12px 4px rgba(0, 0, 0, 0.09);
      }
    }
    .applistItem:nth-child(4n) {
      border-right: none;
    }
    .applistItem:nth-child(4n + 1):nth-last-child(-n + 4) {
      border-bottom: none;
    }
    .applistItem:nth-child(4n + 1):nth-last-child(-n + 4) ~ .applistItem {
      border-bottom: none;
    }
  }
}
</style>
