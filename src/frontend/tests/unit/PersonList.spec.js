import { shallowMount } from '@vue/test-utils'
import PersonList from '@/components/PersonList.vue'


describe('PersonList.vue test', () => {
    it('renders title of list', () => {
        // render the component
        const wrapper = shallowMount(PersonList, {
            propsData: {
                title: "titleOfList"
              }
        })
    
        expect(wrapper.find('.add-row').text()).toBe("titleOfList")
      })
})