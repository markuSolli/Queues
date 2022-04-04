import { shallowMount } from '@vue/test-utils'
import StudentCard from '@/components/StudentCard.vue'


describe('StudentCard.vue test', () => {
    it('renders title card', () => {
        // render the component
        const wrapper = shallowMount(StudentCard, {
            propsData: {
                firstname: "Roger",
                lastname: "Rogersen",
              }
        })
    
        expect(wrapper.find('.element-1').text()).toBe("Roger Rogersen")
      })
})