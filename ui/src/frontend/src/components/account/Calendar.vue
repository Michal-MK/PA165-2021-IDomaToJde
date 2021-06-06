<template>
  <div class="row">
    <div class="col-sm-3">
      <h6 class="mb-0">Calendar</h6>
    </div>
    <div class="col-sm-9 text-secondary">
    </div>
  </div>
  <!--                Monday-->
  <div class="row ml-3">
    <div class="col-sm-3">
      <i class="mb-0">Monday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(0))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
  <!--                Tuesday-->
  <div class="row ml-2">
    <div class="col-sm-3">
      <i class="mb-0">Tuesday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(1))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
  <!--                Wednesday-->
  <div class="row ml-2">
    <div class="col-sm-3">
      <i class="mb-0">Wednesday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(2))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
  <!--                Thursday-->
  <div class="row ml-2">
    <div class="col-sm-3">
      <i class="mb-0">Thursday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(3))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
  <!--                Friday-->
  <div class="row ml-2">
    <div class="col-sm-3">
      <i class="mb-0">Friday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(4))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
  <!--                Saturday-->
  <div class="row ml-2">
    <div class="col-sm-3">
      <i class="mb-0">Saturday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(5))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
  <!--                Sunday-->
  <div class="row ml-2">
    <div class="col-sm-3">
      <i class="mb-0">Sunday</i>
    </div>
    <div class="col-sm-9 text-secondary">
      <div v-for="entry in (getDayEntries(6))" :key="entry.id">
        {{ entry.offer.title }} <i>At</i> {{ entry.entryStart.hour }}:{{ entry.entryStart.minute }} <i>takes</i>
        {{ entry.length.minutes }} <i>minutes</i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Calendar",

  data() {
    return {
      entries: [],
    }
  },

  async mounted() {
    this.entries = await this.$store.getters.getCurrentTimetable();
    console.log("Got entries: " + JSON.stringify(this.entries));
  },

  methods: {
    getDayEntries(intDay) {
      try {
        let f = this.entries.entries.filter(e => e.day === intDay);
        console.log("Filtered for [" + intDay + "] : " + JSON.stringify(f));
        return f;
      } catch (e) {
        console.log(e);
        return [];
      }
    },
  }
}
</script>

<style scoped>

</style>