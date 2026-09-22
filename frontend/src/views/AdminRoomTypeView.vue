<script setup>
import { onMounted, reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import {
  createRoomType,
  getAdminRoomTypes,
  updateRoomType,
} from "../services/admin";
import { useI18n } from "vue-i18n";

const query = reactive({ hotelId: 1, offset: 0, limit: 20 });
const rows = ref([]);
const loading = ref(false);
const dialogOpen = ref(false);
const editingId = ref(null);
const form = reactive({
  hotelId: 1,
  name: "",
  description: "",
  capacity: 2,
  bedType: "",
  basePrice: 0,
  totalRooms: 0,
  status: 1,
});
const { t } = useI18n();
const load = async () => {
  loading.value = true;
  try {
    rows.value = await getAdminRoomTypes(query);
  } finally {
    loading.value = false;
  }
};
const openEditor = (row) => {
  editingId.value = row?.id || null;
  Object.assign(
    form,
    row || {
      hotelId: query.hotelId,
      name: "",
      description: "",
      capacity: 2,
      bedType: "",
      basePrice: 0,
      totalRooms: 0,
      status: 1,
    },
  );
  dialogOpen.value = true;
};
const save = async () => {
  if (editingId.value) await updateRoomType(editingId.value, form);
  else await createRoomType(form);
  ElMessage.success(t("common.requestSubmitted"));
  dialogOpen.value = false;
  load();
};
onMounted(load);
</script>

<template>
  <section class="management-page">
    <div class="container">
      <div class="management-heading">
        <div>
          <span class="eyebrow">{{ t('admin.title') }}</span>
          <h1>{{ t("admin.roomTypeManagement") }}</h1>
        </div>
        <el-button type="primary" @click="openEditor()">{{
          t("admin.addRoomType")
        }}</el-button>
      </div>
      <el-form class="query-form" inline
        ><el-form-item :label="t('room.hotelId')"
          ><el-input-number v-model="query.hotelId" :min="1" /></el-form-item
        ><el-form-item
          ><el-button @click="load">{{
            t("common.search")
          }}</el-button></el-form-item
        ></el-form
      ><el-table v-loading="loading" :data="rows"
        ><el-table-column
          prop="id"
          :label="t('common.id')"
          width="70"
        /><el-table-column
          prop="name"
          :label="t('common.name')"
          min-width="150"
        /><el-table-column
          prop="capacity"
          :label="t('room.capacity')"
        /><el-table-column
          prop="bedType"
          :label="t('room.bedType')"
          min-width="150"
        /><el-table-column
          prop="basePrice"
          :label="t('room.basePrice')"
        /><el-table-column
          prop="totalRooms"
          :label="t('room.totalRooms')"
        /><el-table-column :label="t('common.actions')"
          ><template #default="scope"
            ><el-button link type="primary" @click="openEditor(scope.row)">{{
              t("common.edit")
            }}</el-button></template
          ></el-table-column
        ></el-table
      ><el-dialog
        v-model="dialogOpen"
        :title="editingId ? t('admin.editRoomType') : t('admin.addRoomType')"
        width="620"
        ><el-form label-position="top"
          ><div class="form-grid">
            <el-form-item :label="t('common.name')"
              ><el-input v-model="form.name" /></el-form-item
            ><el-form-item :label="t('room.capacity')"
              ><el-input-number
                v-model="form.capacity"
                :min="1" /></el-form-item
            ><el-form-item :label="t('room.bedType')"
              ><el-input v-model="form.bedType" /></el-form-item
            ><el-form-item :label="t('room.basePrice')"
              ><el-input-number
                v-model="form.basePrice"
                :min="0" /></el-form-item
            ><el-form-item :label="t('room.totalRooms')"
              ><el-input-number
                v-model="form.totalRooms"
                :min="0" /></el-form-item
            ><el-form-item :label="t('common.status')"
              ><el-input-number v-model="form.status" :min="0"
            /></el-form-item>
          </div>
          <el-form-item :label="t('common.description')"
            ><el-input
              v-model="form.description"
              type="textarea" /></el-form-item></el-form
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
