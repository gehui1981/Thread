//(&,|)�������߼�������(&&,||)����������������
//
//&��λ���������ʾ��λ�����㣬&&���߼����������ʾ�߼������루and��.
//
//|| ��λ������� ����ʾ��λ�����㣬||���߼����������ʾ�߼������㡣
//
//��������ֻ�ܲ��������͵�,���߼������������Բ���������,���ҿ��Բ�����ֵ�͵ġ�
//��ͬ�㣺
//����A&B������a�Ƿ�Ϊ�٣���ȻҪ�ж�B��
//����A|B������a�Ƿ�Ϊ�棬��ȻҪ�ж�B��
//������A&&B��A||B����������Ͳ���ȥ�ж�B�ˡ�

public class Test {

    public static void Single() {
        int i = 3;
        if ((i++ > 5) & (i++ < 9)) {
            System.out.println(i);
            System.out.println("��ϲ��ִ������������䣡");
        }
        System.out.println(i);

    }

    public static void Double() {
        int i = 3;
        if ((i++ > 5) && (i++ < 9)) {
            System.out.println(i);
            System.out.println("��ϲ��ִ������������䣡");

        }

        System.out.println(i);
    }

    public static void main(String[] args) {
        Single();
        Double();
        System.out.println(-2 & -1);
        System.out.println(-2 & -1);
    }
}
