<script setup>
import { onMounted, reactive, ref } from "vue";
import { useI18n } from "vue-i18n";
import { ElMessage } from "element-plus";
import { getHotels, updateHotel } from "../services/admin";

const { t } = useI18n();
const rows = ref([]);
const loading = ref(false);
const dialogOpen = ref(false);
const form = reactive({
  id: null,
  name: "",
  address: "",
  phone: "",
  checkInTime: "",
  checkOutTime: "",
  status: 1,
});
const load = async () => {
  loading.value = true;
  try {
    rows.value = await getHotels({ offset: 0, limit: 20 });
  } finally {
    loading.value = false;
  }
};
const edit = (row) => {
  Object.assign(form, row);
  dialogOpen.value = true;
};
const save = async () => {
  await updateHotel(form.id, form);
  ElMessage.success(t("common.requestSubmitted"));
  dialogOpen.value = false;
  await load();
};
onMounted(load);
</script>

<template>
  <section class="management-page">
    <div class="container">
      <div class="management-heading">
        <div>
          <span class="eyebrow">{{ t("admin.title") }}</span>
          <h1>{{ t("admin.hotelManagement") }}</h1>
        </div>
        <el-button @click="load">{{ t("common.refresh") }}</el-button>
      </div>
      <el-table v-loading="loading" :data="rows">
        <el-table-column
          prop="name"
          :label="t('common.name')"
        /><el-table-column
          prop="address"
          :label="t('admin.address')"
          min-width="220"
        />
        <el-table-column
          prop="phone"
          :label="t('admin.phone')"
        /><el-table-column prop="checkInTime" :label="t('admin.checkInTime')" />
        <el-table-column
          prop="checkOutTime"
          :label="t('admin.checkOutTime')"
        /><el-table-column :label="t('common.actions')"
          ><template #default="scope"
            ><el-button link type="primary" @click="edit(scope.row)">{{
              t("common.edit")
            }}</el-button></template
          ></el-table-column
        >
      </el-table>
      <el-dialog
        v-model="dialogOpen"
        :title="t('admin.hotelManagement')"
        width="620"
        ><el-form label-position="top"
          ><div class="form-grid">
            <el-form-item :label="t('common.name')"
              ><el-input v-model="form.name" /></el-form-item
            ><el-form-item :label="t('admin.phone')"
              ><el-input v-model="form.phone"
            /></el-form-item>
            <el-form-item :label="t('admin.checkInTime')"
              ><el-time-picker
                v-model="form.checkInTime"
                value-format="HH:mm:ss" /></el-form-item
            ><el-form-item :label="t('admin.checkOutTime')"
              ><el-time-picker
                v-model="form.checkOutTime"
                value-format="HH:mm:ss"
            /></el-form-item>
          </div>
          <el-form-item :label="t('admin.address')"
            ><el-input v-model="form.address" /></el-form-item></el-form
        ><template #footer
          ><el-button @click="dialogOpen = false">{{
            t("common.cancel")
          }}</el-button
          ><el-button type="primary" @click="save">{{
            t("common.save")
          }}</el-button></template
        ></el-dialog
      >
    </div>
  </section>
</template>
