import { shallowMount } from '@vue/test-utils'
import CourseCard from '@/components/CourseCard.vue'


describe('CourseCard.vue test', () => {
  it('renders title of coursecard', () => {
    // render the component
    const wrapper = shallowMount(CourseCard, {
        propsData: {
          cardInQueue: true,
          course: {
            code: "CODE1",
            title: "TITLE",
          }
          }
    })

    expect(wrapper.find('.queue-element-1').text()).toBe("CODE1 TITLE")
  })
})