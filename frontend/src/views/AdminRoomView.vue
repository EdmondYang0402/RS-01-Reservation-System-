<script setup>
import { onMounted, reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { createRoom, getAdminRooms, updateRoom } from "../services/admin";
import { useI18n } from "vue-i18n";
import { roomStatusKey } from "../utils/i18n";

const query = reactive({ hotelId: 1, offset: 0, limit: 20 });
const rows = ref([]);
const loading = ref(false);
const dialogOpen = ref(false);
const editingId = ref(null);
const form = reactive({
  hotelId: 1,
  roomTypeId: null,
  roomNumber: "",
  floor: 1,
  status: "AVAILABLE",
});
const { t } = useI18n();
const load = async () => {
  loading.value = true;
  try {
    rows.value = await getAdminRooms(query);
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
      roomTypeId: null,
      roomNumber: "",
      floor: 1,
      status: "AVAILABLE",
    },
  );
  dialogOpen.value = true;
};
const save = async () => {
  if (editingId.value) await updateRoom(editingId.value, form);
  else await createRoom(form);
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
          <h1>{{ t("admin.roomManagement") }}</h1>
        </div>
        <el-button type="primary" @click="openEditor()">{{
          t("admin.addRoom")
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
          prop="roomNumber"
          :label="t('room.roomNumber')"
        /><el-table-column
          prop="roomTypeId"
          :label="t('room.roomTypeId')"
        /><el-table-column
          prop="floor"
          :label="t('room.floor')"
        /><el-table-column :label="t('common.status')" min-width="150"
          ><template #default="scope">{{
            t(roomStatusKey(scope.row.status))
          }}</template></el-table-column
        ><el-table-column :label="t('common.actions')"
          ><template #default="scope"
            ><el-button link type="primary" @click="openEditor(scope.row)">{{
              t("common.edit")
            }}</el-button></template
          ></el-table-column
        ></el-table
      ><el-dialog
        v-model="dialogOpen"
        :title="editingId ? t('admin.editRoom') : t('admin.addRoom')"
        width="560"
        ><el-form label-position="top"
          ><div class="form-grid">
            <el-form-item :label="t('room.roomNumber')"
              ><el-input v-model="form.roomNumber" /></el-form-item
            ><el-form-item :label="t('room.roomTypeId')"
              ><el-input-number
                v-model="form.roomTypeId"
                :min="1" /></el-form-item
            ><el-form-item :label="t('room.floor')"
              ><el-input-number v-model="form.floor" /></el-form-item
            ><el-form-item :label="t('common.status')"
              ><el-select v-model="form.status"
                ><el-option
                  v-for="status in ['AVAILABLE', 'OCCUPIED', 'OUT_OF_SERVICE']"
                  :key="status"
                  :label="t(roomStatusKey(status))"
                  :value="status" /></el-select
            ></el-form-item></div></el-form
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
